package plango.auth.application.dto.response;

public record FindLoginIdPreviewResponse(
        String email,
        String maskedLoginId
) {

    public static FindLoginIdPreviewResponse of(
            String email,
            String maskedLoginId
    ) {
        return new FindLoginIdPreviewResponse(email, maskedLoginId);
    }
}
