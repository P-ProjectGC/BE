package plango.chat.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.chat.application.dto.request.ChatMessageSendRequest;
import plango.chat.application.dto.response.ChatMessageResponse;
import plango.chat.application.exception.ChatErrorCode;
import plango.chat.application.exception.ChatException;
import plango.chat.application.mapper.ChatMessageMapper;
import plango.chat.domain.entity.ChatMessage;
import plango.chat.domain.service.ChatMessageService;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberGetService;
import plango.room.domain.entity.Room;
import plango.room.domain.repository.RoomMemberRepository;
import plango.room.domain.service.RoomGetService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageSendUseCase {

    private final ChatMessageService chatMessageService;

    private final ChatMessageMapper chatMessageMapper;

    private final RoomGetService roomGetService;

    private final MemberGetService memberGetService;

    private final RoomMemberRepository roomMemberRepository;

    @Transactional
    public ChatMessageResponse execute(Long roomId, ChatMessageSendRequest request) {
        Room room = roomGetService.getRoomById(roomId);
        Member sender = memberGetService.getById(request.memberId());

        boolean isMember = roomMemberRepository.existsByRoomAndMemberId(room, sender.getId());

        if (!isMember) {
            throw new ChatException(ChatErrorCode.CHAT_MEMBER_NOT_IN_ROOM);
        }

        ChatMessage chatMessage = new ChatMessage(
                room,
                sender,
                request.content()
        );

        ChatMessage saved = chatMessageService.save(chatMessage);

        return chatMessageMapper.toResponse(saved);
    }
}
