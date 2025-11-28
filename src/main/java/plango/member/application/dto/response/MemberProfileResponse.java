package plango.member.application.dto.response;

public record MemberProfileResponse(
        Long memberId,
        String nickname,
        String email,
        String loginId,
        String profileImageUrl
) {
}
