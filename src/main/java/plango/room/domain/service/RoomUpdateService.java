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
import plango.room.domain.entity.RoomRole;
import plango.room.domain.repository.RoomMemberRepository;
import plango.room.domain.repository.RoomRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomUpdateService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final MemberRepository memberRepository;

    public Room updateRoom(Long roomId, Long memberId, RoomUpdateRequest request) {
        boolean isHost = roomMemberRepository.existsByRoom_IdAndMemberIdAndRole(
                roomId,
                memberId,
                RoomRole.OWNER
        );

        if (!isHost) {
            throw new BusinessException(
                    ErrorCode.ROOM_HOST_ONLY.getStatus().value(),
                    ErrorCode.ROOM_HOST_ONLY.getMessage()
            );
        }

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
                RoomMember roomMember = RoomMember.createMember(room, member);
                roomMemberRepository.save(roomMember);
            }
        }
    }

    private void updateHost(Room room, Long newHostId) {

        RoomMember currentHost = roomMemberRepository.findByRoom_IdAndRole(
                room.getId(),
                plango.room.domain.entity.RoomRole.OWNER
        ).orElseThrow(() ->
                new BusinessException(
                        ErrorCode.ROOM_HOST_NOT_IN_MEMBERS.getStatus().value(),
                        ErrorCode.ROOM_HOST_NOT_IN_MEMBERS.getMessage()
                )
        );

        RoomMember newHost = roomMemberRepository.findByRoom_IdAndMemberId(
                room.getId(),
                newHostId
        ).orElseThrow(() ->
                new BusinessException(
                        ErrorCode.MEMBER_NOT_FOUND.getStatus().value(),
                        ErrorCode.MEMBER_NOT_FOUND.getMessage()
                )
        );

        currentHost.changeRole(plango.room.domain.entity.RoomRole.MEMBER);
        newHost.changeRole(plango.room.domain.entity.RoomRole.OWNER);
    }
}
