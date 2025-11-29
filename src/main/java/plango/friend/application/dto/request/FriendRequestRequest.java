package plango.friend.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FriendRequestRequest(
        @NotBlank(message = "친구로 추가할 닉네임은 필수입니다.")
        String targetNickname
) {
}
