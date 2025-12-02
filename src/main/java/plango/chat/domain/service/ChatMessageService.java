package plango.chat.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.chat.domain.entity.ChatMessage;
import plango.chat.domain.repository.ChatMessageRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageService {

    private static final int DEFAULT_PAGE_SIZE = 50;

    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getLatestMessages(Long roomId) {
        PageRequest pageRequest = PageRequest.of(
                0,
                DEFAULT_PAGE_SIZE,
                Sort.by(Sort.Direction.DESC, "id")
        );
        return chatMessageRepository.findByRoomIdOrderByIdDesc(roomId, pageRequest);
    }

    public List<ChatMessage> getMessagesBefore(
            Long roomId,
            Long beforeMessageId,
            int size
    ) {
        int pageSize = size > 0 ? size : DEFAULT_PAGE_SIZE;

        PageRequest pageRequest = PageRequest.of(
                0,
                pageSize,
                Sort.by(Sort.Direction.DESC, "id")
        );
        return chatMessageRepository.findByRoomIdAndIdLessThanOrderByIdDesc(
                roomId,
                beforeMessageId,
                pageRequest
        );
    }
}
