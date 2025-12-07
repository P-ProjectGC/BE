package plango.auth.application.dto.response;

public record SendLoginIdVerificationCodeResponse(
        String email,
        String maskedEmail
) {

    public static SendLoginIdVerificationCodeResponse of(
            String email,
            String maskedEmail
    ) {
        return new SendLoginIdVerificationCodeResponse(
                email,
                maskedEmail
        );
    }
}
