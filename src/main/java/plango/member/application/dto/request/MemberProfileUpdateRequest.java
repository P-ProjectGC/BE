package plango.member.application.dto.request;

public record MemberProfileUpdateRequest(
        String name,
        String nickname,
        String profileImageUrl
) {
}
