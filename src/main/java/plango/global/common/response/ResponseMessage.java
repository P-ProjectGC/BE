package plango.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    // ===== 공통 =====
    SUCCESS(0, "성공"),

    // ===== 프로필 =====
    MEMBER_PROFILE_GET_SUCCESS(0, "프로필 조회 성공"),
    MEMBER_PROFILE_UPDATE_SUCCESS(0, "프로필 수정 성공");

    private final int code;
    private final String message;
}
