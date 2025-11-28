package plango.room.domain.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;
import plango.member.domain.entity.Member;
import plango.member.domain.repository.MemberRepository;
import plango.room.application.dto.request.RoomUpdateRequest;
import plango.room.domain.entity.Room;
import plango.room.domain.entity.RoomMember;
import plango.room.domain.repository.RoomMemberRepository;
import plango.room.domain.repository.RoomRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomUpdateService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final MemberRepository memberRepository;

    public Room updateRoom(Long roomId, RoomUpdateRequest request) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_NOT_FOUND.getStatus().value(), ErrorCode.ROOM_NOT_FOUND.getMessage()));

        validateHostIncludedInMembers(request);

        updateBasicInfo(room, request);

        updateMembers(room, request.memberIds(), request.hostId());

        updateHost(room, request.hostId());

        return room;
    }

    private void validateHostIncludedInMembers(RoomUpdateRequest request) {
        if (!request.memberIds().contains(request.hostId())) {
            throw new BusinessException(ErrorCode.ROOM_HOST_NOT_IN_MEMBERS.getStatus().value(), ErrorCode.ROOM_HOST_NOT_IN_MEMBERS.getMessage());
        }
    }

    private void updateBasicInfo(Room room, RoomUpdateRequest request) {
        room.updateName(request.roomName());
        room.updateMemo(request.memo());
    }

    private void updateMembers(Room room, List<Long> newMemberIds, Long hostId) {
        List<RoomMember> currentMembers = roomMemberRepository.findByRoom(room);

        Set<Long> currentIds = new HashSet<>();
        for (RoomMember roomMember : currentMembers) {
            currentIds.add(roomMember.getMember().getId());
        }

        Set<Long> newIds = new HashSet<>(newMemberIds);

        Set<Long> toRemove = new HashSet<>(currentIds);
        toRemove.removeAll(newIds);

        Set<Long> toAdd = new HashSet<>(newIds);
        toAdd.removeAll(currentIds);

        if (!toRemove.isEmpty()) {
            toRemove.remove(hostId);

            if (!toRemove.isEmpty()) {
                roomMemberRepository.deleteByRoomAndMemberIdIn(room, toRemove);
            }
        }

        if (!toAdd.isEmpty()) {
            List<Member> addedMembers = memberRepository.findAllById(toAdd);

            if (addedMembers.size() != toAdd.size()) {
                throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND.getStatus().value(), ErrorCode.MEMBER_NOT_FOUND.getMessage());
            }

            for (Member member : addedMembers) {
                RoomMember roomMember = RoomMember.create(room, member);
                roomMemberRepository.save(roomMember);
            }
        }
    }

    private void updateHost(Room room, Long hostId) {
        boolean exists = roomMemberRepository.existsByRoomAndMemberId(room, hostId);

        if (!exists) {
            throw new BusinessException(ErrorCode.ROOM_HOST_NOT_IN_MEMBERS.getStatus().value(), ErrorCode.ROOM_HOST_NOT_IN_MEMBERS.getMessage());
        }

        Member owner = memberRepository.findById(hostId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND.getStatus().value(), ErrorCode.MEMBER_NOT_FOUND.getMessage()));

        room.updateOwner(owner);
    }
}
