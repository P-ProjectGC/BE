package plango.auth.application.dto.response;

import lombok.Builder;

@Builder
public record KakaoLoginResponse(
        Long memberId,
        String email,
        String nickname,
        String profileImageUrl,
        boolean newMember,
        String accessToken,
        String refreshToken
) {
}
