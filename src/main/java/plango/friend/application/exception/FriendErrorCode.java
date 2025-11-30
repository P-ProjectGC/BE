package plango.friend.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FriendErrorCode {

    FRIEND_NOT_FOUND(HttpStatus.NOT_FOUND, "친구 관계를 찾을 수 없습니다."),
    CANNOT_REQUEST_SELF(HttpStatus.BAD_REQUEST, "자기 자신에게는 친구 요청을 보낼 수 없습니다."),
    FRIEND_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 친구 상태입니다."),
    FRIEND_REQUEST_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 친구 요청이 존재합니다."),
    FRIEND_TARGET_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 닉네임의 사용자를 찾을 수 없습니다."),
    INVALID_FRIEND_STATUS(HttpStatus.BAD_REQUEST, "요청에 사용할 수 없는 친구 상태입니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatusCode() {
        return status.value();
    }
}
