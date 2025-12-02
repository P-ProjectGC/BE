package plango.chat.application.exception;

import lombok.Getter;
import plango.global.common.exception.BusinessException;

@Getter
public class ChatException extends BusinessException {

    private final ChatErrorCode errorCode;

    public ChatException(ChatErrorCode errorCode) {
        super(errorCode.getStatusCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
