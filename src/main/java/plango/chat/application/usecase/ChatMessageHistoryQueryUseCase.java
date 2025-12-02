package plango.chat.application.usecase;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.chat.application.dto.response.ChatMessageResponse;
import plango.chat.application.mapper.ChatMessageMapper;
import plango.chat.domain.entity.ChatMessage;
import plango.chat.domain.service.ChatMessageService;
import plango.room.domain.service.RoomGetService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageHistoryQueryUseCase {

    private final ChatMessageService chatMessageService;

    private final ChatMessageMapper chatMessageMapper;

    private final RoomGetService roomGetService;

    public List<ChatMessageResponse> execute(Long roomId) {
        roomGetService.getRoomById(roomId);

        List<ChatMessage> messages = chatMessageService.getLatestMessages(roomId);

        Collections.reverse(messages);

        return chatMessageMapper.toResponseList(messages);
    }
}
