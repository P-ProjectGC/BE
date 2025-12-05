package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.MemberLoginRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.mapper.KakaoAuthMapper;
import plango.auth.domain.service.JwtTokenProvider;
import plango.auth.domain.service.RefreshTokenService;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
public class MemberLoginUseCase {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public KakaoLoginResponse execute(MemberLoginRequest request) {
        Member member = memberService.loginByLoginId(
                request.loginId(),
                request.password()
        );

        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        refreshTokenService.saveOrUpdate(member, refreshToken);

        return KakaoAuthMapper.toKakaoLoginResponse(
                member,
                false,
                accessToken,
                refreshToken
        );
    }
}
