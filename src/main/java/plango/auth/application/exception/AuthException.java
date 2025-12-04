package plango.auth.application.exception;

import lombok.Getter;
import plango.global.common.exception.BusinessException;

@Getter
public class AuthException extends BusinessException {

    private final AuthErrorCode errorCode;

    public AuthException(AuthErrorCode errorCode) {
        super(errorCode.getStatusCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
