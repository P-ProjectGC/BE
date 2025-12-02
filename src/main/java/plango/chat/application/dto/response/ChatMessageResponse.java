package plango.chat.application.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;

public record ChatMessageResponse(
        Long messageId,
        Long roomId,
        Long senderId,
        String senderNickname,
        String content,
        LocalDateTime createdAt
) {

    @Builder
    public ChatMessageResponse {
    }
}
