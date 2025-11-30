package plango.friend.application.exception;

import lombok.Getter;
import plango.global.common.exception.BusinessException;

@Getter
public class FriendException extends BusinessException {

    private final FriendErrorCode errorCode;

    public FriendException(FriendErrorCode errorCode) {
        super(errorCode.getStatusCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
