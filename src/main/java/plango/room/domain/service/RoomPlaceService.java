package plango.room.domain.service;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;
import plango.room.domain.entity.Room;
import plango.room.domain.entity.RoomPlace;
import plango.room.domain.repository.RoomRepository;
import plango.room.domain.repository.RoomPlaceRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomPlaceService {

    private final RoomRepository roomRepository;
    private final RoomPlaceRepository roomPlaceRepository;

    public RoomPlace createPlace(Long roomId,
                                 Long memberId,
                                 String name,
                                 String address) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_NOT_FOUND));

        RoomPlace roomPlace = RoomPlace.builder()
                .room(room)
                .name(name)
                .address(address)
                .createdByMemberId(memberId)
                .build();

        return roomPlaceRepository.save(roomPlace);
    }

    @Transactional(readOnly = true)
    public List<RoomPlace> getPlaces(Long roomId) {

        return roomPlaceRepository
                .findAllByRoomIdAndDeletedFalseOrderByIdAsc(roomId);
    }

    public void deletePlace(Long placeId) {

        RoomPlace place = roomPlaceRepository.findById(placeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROOM_PLACE_NOT_FOUND));

        place.markDeleted();
    }
}
