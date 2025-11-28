package plango.room.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plango.room.application.dto.request.RoomUpdateRequest;
import plango.room.application.dto.response.RoomUpdateResponse;
import plango.room.application.mapper.RoomMapper;
import plango.room.domain.entity.Room;
import plango.room.domain.service.RoomUpdateService;

@Service
@RequiredArgsConstructor
public class UpdateRoomUseCase {

    private final RoomUpdateService roomUpdateService;
    private final RoomMapper roomMapper;

    public RoomUpdateResponse updateRoom(Long roomId, RoomUpdateRequest request) {
        // 1. 도메인 서비스에 위임해서 엔티티 수정
        Room updatedRoom = roomUpdateService.updateRoom(roomId, request);

        // 2. 엔티티 → Response DTO 변환
        return roomMapper.toRoomUpdateResponse(updatedRoom);
    }
}
