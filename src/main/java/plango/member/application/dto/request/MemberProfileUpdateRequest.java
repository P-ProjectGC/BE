package plango.member.application.dto.request;

public record MemberProfileUpdateRequest(
        String nickname,
        String profileImageUrl
) {
}
