package plango.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    // ===== 공통 =====
    SUCCESS(0, "성공");

    private final int code;
    private final String message;
}
