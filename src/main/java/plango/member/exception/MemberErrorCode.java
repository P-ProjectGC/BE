package plango.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member not found"),
    INVALID_MEMBER_STATUS(HttpStatus.BAD_REQUEST, "Invalid member status"),

    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "이미 사용 중인 닉네임입니다."),

    // 아이디 또는 비밀번호가 틀린 경우
    INVALID_MEMBER_CREDENTIAL(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
