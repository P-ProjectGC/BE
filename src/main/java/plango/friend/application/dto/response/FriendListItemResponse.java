package plango.friend.application.dto.response;

public record FriendListItemResponse(
        Long friendId,
        Long memberId,
        String name,
        String nickname,
        String profileImageUrl,
        String loginType
) {
}
