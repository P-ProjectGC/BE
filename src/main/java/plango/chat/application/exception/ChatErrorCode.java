package plango.chat.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode {

    CHAT_MEMBER_NOT_IN_ROOM(HttpStatus.FORBIDDEN, "여행방 멤버만 채팅에 참여할 수 있습니다."),
    CHAT_MESSAGE_EMPTY(HttpStatus.BAD_REQUEST, "메시지 내용은 비어 있을 수 없습니다.");

    private final HttpStatus status;

    private final String message;

    public int getStatusCode() {
        return status.value();
    }
}
