package plango.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    KAKAO_LOGIN_SUCCESS("카카오 로그인에 성공했습니다.");

    private final String message;
}
