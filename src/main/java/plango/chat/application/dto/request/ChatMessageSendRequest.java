package plango.chat.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChatMessageSendRequest(
        @NotNull(message = "회원 ID는 필수입니다.")
        Long memberId,
        @NotBlank(message = "메시지 내용은 필수입니다.")
        @Size(max = 1000, message = "메시지는 최대 1000자까지 가능합니다.")
        String content
) {
}
