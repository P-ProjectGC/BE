package plango.room.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberGetService;
import plango.room.application.dto.request.RoomCreateRequest;
import plango.room.application.dto.response.RoomCreateResponse;
import plango.room.application.mapper.RoomMapper;
import plango.room.domain.entity.Room;
import plango.room.domain.service.RoomMemberSaveService;
import plango.room.domain.service.RoomSaveService;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateRoomUseCase {

    private final MemberGetService memberGetService;
    private final RoomSaveService roomSaveService;
    private final RoomMemberSaveService roomMemberSaveService;
    private final RoomMapper roomMapper;

    public RoomCreateResponse execute(Long ownerId, RoomCreateRequest request) {

        Member owner = memberGetService.getById(ownerId);

        Room room = roomMapper.toEntity(request, owner);
        Room savedRoom = roomSaveService.save(room);

        roomMemberSaveService.addOwner(savedRoom, owner);

        request.memberIds().forEach(memberId -> {
            Member member = memberGetService.getById(memberId);
            roomMemberSaveService.addMember(savedRoom, member);
        });

        return roomMapper.toCreateResponse(savedRoom);
    }
}
