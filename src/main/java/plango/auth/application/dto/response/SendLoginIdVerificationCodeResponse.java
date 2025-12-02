package plango.auth.application.dto.response;

public record SendLoginIdVerificationCodeResponse(
        String email,
        String maskedEmail,
        String verificationCode
) {

    public static SendLoginIdVerificationCodeResponse of(
            String email,
            String maskedEmail,
            String verificationCode
    ) {
        return new SendLoginIdVerificationCodeResponse(
                email,
                maskedEmail,
                verificationCode
        );
    }
}
