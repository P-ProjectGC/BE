package plango.auth.application.dto.response;

import plango.member.domain.entity.Member;

/**
 * 카카오 로그인 응답 DTO
 */
public record KakaoLoginResponse(
        Long memberId,
        String email,
        String nickname,
        String profileImageUrl,
        boolean newMember
) {

    public static KakaoLoginResponse of(Member member, boolean newMember) {
        return new KakaoLoginResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getProfileImageUrl(),
                newMember
        );
    }
}
