package plango.chat.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import plango.chat.application.dto.request.ChatMessageSendRequest;
import plango.chat.application.dto.response.ChatMessageResponse;
import plango.chat.application.usecase.ChatMessageSendUseCase;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatMessageSendUseCase chatMessageSendUseCase;

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/rooms/{roomId}")
    public void sendChatMessage(
            @DestinationVariable Long roomId,
            ChatMessageSendRequest request
    ) {
        ChatMessageResponse response = chatMessageSendUseCase.execute(roomId, request);

        String destination = "/topic/rooms/" + roomId;

        messagingTemplate.convertAndSend(destination, response);
    }
}
