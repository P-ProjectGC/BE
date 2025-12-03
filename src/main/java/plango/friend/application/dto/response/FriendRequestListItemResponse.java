package plango.friend.application.dto.response;

import java.time.LocalDateTime;

public record FriendRequestListItemResponse(
        Long friendId,
        Long memberId,
        String nickname,
        String profileImageUrl,
        LocalDateTime createdAt
) {
}
