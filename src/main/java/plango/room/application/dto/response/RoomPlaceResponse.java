package plango.room.application.dto.response;

import lombok.Builder;

@Builder
public record RoomPlaceResponse(
        Long placeId,
        String name,
        String address,
        Long createdByMemberId
) {}
