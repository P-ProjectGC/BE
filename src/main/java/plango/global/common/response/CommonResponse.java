package plango.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 모든 API 응답을 감싸는 공통 응답 객체
 *  - code    : 비즈니스/HTTP 코드
 *  - message : 응답 메시지
 *  - data    : 실제 응답 데이터
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    private int code;
    private String message;
    private T data;

    // =======================
    // 성공 응답
    // =======================

    /** ResponseMessage 기반 성공 응답 */
    public static <T> CommonResponse<T> success(ResponseMessage message, T data) {
        return CommonResponse.<T>builder()
                .code(message.getCode())
                .message(message.getMessage())
                .data(data)
                .build();
    }

    /** 임의 메시지 기반 성공 응답 */
    public static <T> CommonResponse<T> success(String message, T data) {
        return CommonResponse.<T>builder()
                .code(0)
                .message(message)
                .data(data)
                .build();
    }

    // =======================
    // 실패 응답 (기존 + createFailure 추가)
    // =======================

    /** 간단 실패 응답 (code + message, data 는 null) */
    public static <T> CommonResponse<T> fail(ResponseMessage message) {
        return CommonResponse.<T>builder()
                .code(message.getCode())
                .message(message.getMessage())
                .data(null)
                .build();
    }

    /** GlobalExceptionHandler 에서 사용하는 실패 응답 (data 없음) */
    public static <T> CommonResponse<T> createFailure(int code, String message) {
        return CommonResponse.<T>builder()
                .code(code)
                .message(message)
                .data(null)
                .build();
    }

    /** GlobalExceptionHandler 에서 사용하는 실패 응답 (data 포함) */
    public static <T> CommonResponse<T> createFailure(int code, String message, T data) {
        return CommonResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}
