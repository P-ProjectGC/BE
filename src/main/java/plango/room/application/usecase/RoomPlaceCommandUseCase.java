package plango.room.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.room.application.dto.request.RoomPlaceCreateRequest;
import plango.room.application.dto.response.RoomPlaceResponse;
import plango.room.application.mapper.RoomPlaceMapper;
import plango.room.domain.entity.RoomPlace;
import plango.room.domain.service.RoomPlaceService;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomPlaceCommandUseCase {

    private final RoomPlaceService roomPlaceService;

    public RoomPlaceResponse createRoomPlace(Long roomId,
                                             Long memberId,
                                             RoomPlaceCreateRequest request) {

        RoomPlace roomPlace = roomPlaceService.createPlace(
            roomId,
            memberId,
            request.name(),
            request.address(),
            request.googlePlaceId(),
            request.formattedAddress(),
            request.latitude(),
            request.longitude()
        );

        return RoomPlaceMapper.toResponse(roomPlace);
    }

    public void deleteRoomPlace(Long roomId, Long roomPlaceId) {
        roomPlaceService.deletePlace(roomPlaceId);
    }
}
