package plango.chat.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plango.chat.application.dto.request.ChatMessageSendRequest;
import plango.chat.application.dto.response.ChatMessageResponse;
import plango.chat.application.usecase.ChatMessageHistoryQueryUseCase;
import plango.chat.application.usecase.ChatMessageSendUseCase;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms/{roomId}/chats")
@Tag(name = "Room Chat", description = "여행방 내 실시간 채팅 API")
public class ChatMessageController {

    private final ChatMessageSendUseCase chatMessageSendUseCase;

    private final ChatMessageHistoryQueryUseCase chatMessageHistoryQueryUseCase;

    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    @Operation(
            summary = "채팅 메시지 전송",
            description = "여행방 멤버가 채팅 메시지를 전송합니다. 성공 시 WebSocket 구독자에게도 브로드캐스트됩니다."
    )
    public CommonResponse<ChatMessageResponse> sendMessage(
            @PathVariable Long roomId,
            @Valid @RequestBody ChatMessageSendRequest request
    ) {
        ChatMessageResponse response = chatMessageSendUseCase.execute(roomId, request);

        String destination = "/topic/rooms/" + roomId;

        messagingTemplate.convertAndSend(destination, response);

        return CommonResponse.success(ResponseMessage.CHAT_MESSAGE_SEND_SUCCESS, response);
    }

    @GetMapping
    @Operation(
            summary = "채팅 메시지 목록 조회",
            description = "여행방의 최근 채팅 메시지 목록을 조회합니다. 기본 50개까지 조회합니다."
    )
    public CommonResponse<List<ChatMessageResponse>> getMessages(
            @PathVariable Long roomId
    ) {
        List<ChatMessageResponse> responses = chatMessageHistoryQueryUseCase.execute(roomId);
        return CommonResponse.success(ResponseMessage.CHAT_MESSAGE_LIST_GET_SUCCESS, responses);
    }
}
