package plango.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {

    private int code;
    private String message;
    private T data;


    /** 공통 ResponseMessage 기반 성공 응답 */
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

    /** ResponseMessage 기반 성공 응답 (기존 createSuccess 호출 호환용) */
    public static <T> CommonResponse<T> createSuccess(ResponseMessage message, T data) {
        return success(message, data);
    }

    /** 문자열 메시지 기반 성공 응답 (기존 createSuccess 호출 호환용) */
    public static <T> CommonResponse<T> createSuccess(String message, T data) {
        return success(message, data);
    }

    /** ResponseMessage 기반 실패 응답 (data 는 null) */
    public static <T> CommonResponse<T> fail(ResponseMessage message) {
        return CommonResponse.<T>builder()
                .code(message.getCode())
                .message(message.getMessage())
                .data(null)
                .build();
    }

    /** 코드 + 메시지 기반 실패 응답 (data 없음, GlobalExceptionHandler 등에서 사용) */
    public static <T> CommonResponse<T> fail(int code, String message) {
        return CommonResponse.<T>builder()
                .code(code)
                .message(message)
                .data(null)
                .build();
    }

    /** 코드 + 메시지 + data 기반 실패 응답 */
    public static <T> CommonResponse<T> fail(int code, String message, T data) {
        return CommonResponse.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}
