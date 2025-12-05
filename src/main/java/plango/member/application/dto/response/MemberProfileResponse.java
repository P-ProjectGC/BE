package plango.member.application.dto.response;

import plango.member.domain.entity.LoginType;

public record MemberProfileResponse(
        Long memberId,
        String name,
        String nickname,
        String email,
        String loginId,
        String profileImageUrl,
        LoginType loginType
) {
}
