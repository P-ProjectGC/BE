package plango.global.common.exception;

import lombok.Getter;
import plango.global.common.exception.ErrorCode;

@Getter
public class BusinessException extends RuntimeException {
    private final int statusCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.statusCode = errorCode.getStatus().value();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.statusCode = code;
    }
}
