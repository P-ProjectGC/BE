package plango.member.application.dto.response;

public record MemberSearchResponse(
        Long memberId,
        String nickname,
        String profileImageUrl
) {
}
