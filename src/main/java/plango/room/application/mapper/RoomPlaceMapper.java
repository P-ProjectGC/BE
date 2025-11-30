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
            .id(roomPlace.getId())
            .name(roomPlace.getName())
            .address(roomPlace.getAddress())
            .googlePlaceId(roomPlace.getGooglePlaceId())
            .formattedAddress(roomPlace.getFormattedAddress())
            .latitude(roomPlace.getLatitude())
            .longitude(roomPlace.getLongitude())
            .createdByMemberId(roomPlace.getCreatedByMemberId())
            .build();
    }
}
