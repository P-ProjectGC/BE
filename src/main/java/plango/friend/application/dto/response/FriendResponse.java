package plango.friend.application.dto.response;

public record FriendResponse(
        Long friendId,
        Long requesterId,
        Long receiverId,
        String receiverNickname,
        String status
) {
}
