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
    KAKAO_SERVER_ERROR(HttpStatus.BAD_GATEWAY, "카카오 인증 서버와의 통신에 실패했습니다."),
    ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "여행방을 찾을 수 없습니다."),
    ROOM_HOST_NOT_IN_MEMBERS(HttpStatus.BAD_REQUEST, "방장은 여행방 멤버 목록 안에 포함되어야 합니다."),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "멤버를 찾을 수 없습니다."),
    INVALID_DAY_INDEX(HttpStatus.BAD_REQUEST, "날짜가 유효한 범위를 벗어났습니다."),
    ROOM_PLACE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 여행방 장소를 찾을 수 없습니다."),
    ROOM_HOST_ONLY(HttpStatus.FORBIDDEN, "방장만 수행할 수 있는 작업입니다."),
    ROOM_SCHEDULE_NOT_FOUND(HttpStatus.BAD_REQUEST, "일정을 찾을 수 없습니다.");// 이미 있을 수도 있음

    private final HttpStatus status;
    private final String message;

    public int getStatusCode() {
        return status.value();
    }

    public String getFormatted(String str) {
        return String.format(this.message, str);
    }
}
