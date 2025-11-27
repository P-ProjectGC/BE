package plango.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member not found"),
    INVALID_MEMBER_STATUS(HttpStatus.BAD_REQUEST, "Invalid member status");

    private final HttpStatus status;
    private final String message;
}
