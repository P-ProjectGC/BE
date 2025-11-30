package plango.room.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.room.application.dto.response.RoomPlaceResponse;
import plango.room.application.mapper.RoomPlaceMapper;
import plango.room.domain.service.RoomPlaceService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomPlaceQueryUseCase {

    private final RoomPlaceService roomPlaceService;

    public List<RoomPlaceResponse> getPlaces(Long roomId) {
        return roomPlaceService.getPlaces(roomId)
            .stream()
            .map(RoomPlaceMapper::toResponse)
            .toList();
    }
}
