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
import plango.auth.domain.service.SocialAccountService;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoLoginUseCase {

    private static final String KAKAO_PROVIDER = "KAKAO";

    private final KakaoAuthService kakaoAuthService;
    private final SocialAccountService socialAccountService;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public KakaoLoginResponse execute(KakaoLoginRequest request) {
        KakaoUserInfoResponse kakaoUserInfo =
                kakaoAuthService.getUserInfoByAuthorizationCode(request.authorizationCode());

        Long kakaoId = kakaoUserInfo.id();
        String email = kakaoUserInfo.getEmail();

        SocialAccount socialAccount =
                socialAccountService.findByProviderAndProviderUserId(
                                KAKAO_PROVIDER,
                                kakaoId.toString()
                        )
                        .orElse(null);

        Member member;
        boolean isNewMember;

        if (socialAccount == null) {
            if (email == null || email.isBlank()) {
                member = memberService.save(
                        KakaoAuthMapper.toMember(kakaoUserInfo)
                );
            } else {
                member = memberService.findByEmail(email)
                        .orElseGet(
                                () -> memberService.save(
                                        KakaoAuthMapper.toMember(kakaoUserInfo)
                                )
                        );
            }

            socialAccount = socialAccountService.createAndSaveKakaoAccount(
                    kakaoId.toString(),
                    member
            );

            isNewMember = true;
        } else {
            member = socialAccount.getMember();
            isNewMember = false;
        }

        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        return KakaoAuthMapper.toKakaoLoginResponse(
                member,
                isNewMember,
                accessToken,
                refreshToken
        );
    }
}
