package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.TokenReissueRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.exception.AuthErrorCode;
import plango.auth.application.exception.AuthException;
import plango.auth.application.mapper.KakaoAuthMapper;
import plango.auth.domain.entity.RefreshToken;
import plango.auth.domain.service.JwtTokenProvider;
import plango.auth.domain.service.RefreshTokenService;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
public class TokenReissueUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public KakaoLoginResponse execute(TokenReissueRequest request) {
        String refreshToken = request.refreshToken();

        Long memberId = jwtTokenProvider.getMemberIdFromRefreshToken(refreshToken);

        RefreshToken storedRefreshToken = refreshTokenService.getByMemberId(memberId);

        if (!storedRefreshToken.getToken().equals(refreshToken)) {
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        Member member = memberService.getById(memberId);

        String newAccessToken = jwtTokenProvider.createAccessToken(member.getId());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        refreshTokenService.saveOrUpdate(member, newRefreshToken);

        return KakaoAuthMapper.toKakaoLoginResponse(
                member,
                false,
                newAccessToken,
                newRefreshToken
        );
    }
}
