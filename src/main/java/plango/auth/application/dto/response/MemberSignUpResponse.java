package plango.auth.application.dto.response;

public record MemberSignUpResponse(
        Long memberId,
        String name,
        String nickname,
        String loginId,
        String email
) {
}
