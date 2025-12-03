package plango.friend.application.dto.response;

public record FriendListItemResponse(
        Long friendId,
        Long memberId,
        String nickname,
        String profileImageUrl,
        String loginType
) {
}
