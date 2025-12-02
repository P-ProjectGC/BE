package plango.room.application.dto.response;

import lombok.Builder;

public record RoomMemberResponse(
        Long memberId,
        String nickname,
        String profileImageUrl,
        boolean host
) {

    @Builder
    public RoomMemberResponse {
    }
}
