package plango.chat.application.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import plango.chat.application.dto.response.ChatMessageResponse;
import plango.chat.domain.entity.ChatMessage;

@Component
public class ChatMessageMapper {

    public ChatMessageResponse toResponse(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .messageId(chatMessage.getId())
                .roomId(chatMessage.getRoom().getId())
                .senderId(chatMessage.getSender().getId())
                .senderNickname(chatMessage.getSender().getNickname())
                .content(chatMessage.getContent())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }

    public List<ChatMessageResponse> toResponseList(List<ChatMessage> messages) {
        return messages.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
