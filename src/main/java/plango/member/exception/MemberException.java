package plango.member.exception;

import lombok.Getter;
import plango.global.common.exception.BusinessException;

@Getter
public class MemberException extends BusinessException {

    private final MemberErrorCode errorCode;

    public MemberException(MemberErrorCode errorCode) {
        super(errorCode.getStatus().value(), errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
