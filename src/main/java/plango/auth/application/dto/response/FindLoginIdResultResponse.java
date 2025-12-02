package plango.auth.application.dto.response;

public record FindLoginIdResultResponse(
        String loginId
) {

    public static FindLoginIdResultResponse from(String loginId) {
        return new FindLoginIdResultResponse(loginId);
    }
}
