package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.KakaoLoginRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.dto.response.KakaoUserInfoResponse;
import plango.auth.application.mapper.KakaoAuthMapper;
import plango.auth.domain.entity.SocialAccount;
import plango.auth.domain.service.JwtTokenProvider;
import plango.auth.domain.service.KakaoAuthService;
import plango.auth.domain.service.RefreshTokenService;
import plango.auth.domain.service.SocialAccountService;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
public class KakaoLoginUseCase {

    private final KakaoAuthService kakaoAuthService;
    private final SocialAccountService socialAccountService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public KakaoLoginResponse execute(KakaoLoginRequest request) {
        KakaoUserInfoResponse userInfo = kakaoAuthService.getUserInfoByAuthorizationCode(
                request.authorizationCode()
        );

        String email = userInfo.getEmail();
        String nickname = userInfo.getNickname();
        String profileImageUrl = userInfo.getProfileImageUrl();
        String providerUserId = String.valueOf(userInfo.id());

        boolean isNewMember;
        Member member;

        SocialAccount socialAccount = socialAccountService
                .findByProviderAndProviderUserId("KAKAO", providerUserId)
                .orElse(null);

        if (socialAccount == null) {
            member = Member.createKakaoMember(
                    email,
                    nickname,
                    profileImageUrl
            );
            memberService.save(member);
            socialAccountService.createAndSaveKakaoAccount(providerUserId, member);
            isNewMember = true;
        } else {
            member = socialAccount.getMember();
            isNewMember = false;
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        refreshTokenService.saveOrUpdate(member, refreshToken);

        return KakaoAuthMapper.toKakaoLoginResponse(
                member,
                isNewMember,
                accessToken,
                refreshToken
        );
    }
}
