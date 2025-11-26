package plango.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 비즈니스 응답 코드 & 메시지 정의
 */
@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    // ===== 공통 =====
    SUCCESS(0, "성공"),

    // ===== 인증 / 카카오 로그인 =====
    KAKAO_LOGIN_SUCCESS(1000, "카카오 로그인에 성공했습니다.");

    private final int code;
    private final String message;
}
