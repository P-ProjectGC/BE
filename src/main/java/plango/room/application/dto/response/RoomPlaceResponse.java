package plango.room.application.dto.response;

import lombok.Builder;

@Builder
public record RoomPlaceResponse(

        Long id,
        String name,
        String address,
        String formattedAddress,
        String googlePlaceId,
        Double latitude,
        Double longitude,
        Long createdByMemberId
) {
}
