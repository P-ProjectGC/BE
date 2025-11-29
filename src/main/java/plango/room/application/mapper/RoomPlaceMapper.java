package plango.room.application.mapper;

import lombok.experimental.UtilityClass;
import plango.room.application.dto.response.RoomPlaceResponse;
import plango.room.domain.entity.RoomPlace;

@UtilityClass
public class RoomPlaceMapper {

    public RoomPlaceResponse toResponse(RoomPlace roomPlace) {
        if (roomPlace == null) {
            return null;
        }

        return RoomPlaceResponse.builder()
            .placeId(roomPlace.getId())
            .name(roomPlace.getName())
            .address(roomPlace.getAddress())
            .createdByMemberId(roomPlace.getCreatedByMemberId())
            .build();
    }
}
