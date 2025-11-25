package plango.global.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final int statusCode;

    public BusinessException(int code, String message) {
        super(message);
        this.statusCode = code;
    }

    // 🔽 새로 추가
    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getStatusCode(), errorCode.getMessage());
    }
}
