package plango.room.application.dto.request;

public record RoomPlaceCreateRequest(
        String name,
        String address,
        String googlePlaceId,
        String formattedAddress,
        Double latitude,
        Double longitude
) {}
