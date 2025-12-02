package plango.room.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.room.application.dto.response.RoomDetailResponse;
import plango.room.application.dto.response.RoomMemberResponse;
import plango.room.application.mapper.RoomMapper;
import plango.room.domain.entity.Room;
import plango.room.domain.entity.RoomMember;
import plango.room.domain.service.RoomGetService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRoomDetailUseCase {

    private final RoomGetService roomGetService;

    private final RoomMapper roomMapper;

    @Transactional(readOnly = true)
    public RoomDetailResponse execute(Long roomId, Long memberId) {
        Room room = roomGetService.getRoomById(roomId);

        boolean host = room.getOwnerId() != null
                && room.getOwnerId().equals(memberId);

        List<RoomMember> roomMembers = roomGetService.getRoomMembersByRoomId(roomId);

        List<RoomMemberResponse> memberResponses = roomMembers.stream()
                .map(roomMapper::toRoomMemberResponse)
                .toList();

        return roomMapper.toRoomDetailResponse(
                room,
                host,
                memberResponses
        );
    }
}
