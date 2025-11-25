package plango.global.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    BIND_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    ARGUMENT_NOT_VALID_EXCEPTION(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다."),
    ARGUMENT_TYPE_MISMATCH_EXCEPTION(HttpStatus.BAD_REQUEST, "%s 파라미터의 타입이 잘못되었습니다."),
    MISMATCHED_INPUT_EXCEPTION(HttpStatus.BAD_REQUEST, "%s 데이터의 타입이 잘못되었습니다."),
    JSON_PARSE_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 JSON 형식입니다."),
    KAKAO_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "카카오 액세스 토큰이 유효하지 않습니다."),
    KAKAO_SERVER_ERROR(HttpStatus.BAD_GATEWAY, "카카오 인증 서버와의 통신에 실패했습니다.");

    private final HttpStatus status;
    private final String message;

    public int getStatusCode() {
        return status.value();
    }

    public String getFormatted(String str) {
        return String.format(this.message, str);
    }
}
