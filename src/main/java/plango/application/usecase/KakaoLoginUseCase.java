package plango.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.application.dto.request.KakaoLoginRequest;
import plango.application.dto.response.LoginResponse;
import plango.domain.entity.SocialAccount;
import plango.domain.entity.SocialProvider;
import plango.domain.repository.SocialAccountRepository;
import plango.application.dto.response.KakaoUserInfo;
import plango.domain.entity.Member;
import plango.domain.repository.MemberRepository;
import plango.global.auth.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class KakaoLoginUseCase {

    private final KakaoOAuthService kakaoOAuthService;
    private final MemberRepository memberRepository;
    private final SocialAccountRepository socialAccountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse execute(KakaoLoginRequest request) {

        KakaoUserInfo kakaoUserInfo = kakaoOAuthService.getUserInfo(request.getAccessToken());

        SocialAccount socialAccount = socialAccountRepository
                .findByProviderAndProviderUid(
                        SocialProvider.KAKAO,
                        kakaoUserInfo.getProviderUid()
                )
                .orElseGet(() -> createSocialAccount(kakaoUserInfo));

        Member member = socialAccount.getMember();

        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        return new LoginResponse(
                member.getId(),
                member.getNickname(),
                member.getProfileImageUrl(),
                accessToken,
                refreshToken
        );
    }

    private SocialAccount createSocialAccount(KakaoUserInfo kakaoUserInfo) {

        Member member = createMemberIfNeeded(kakaoUserInfo);

        SocialAccount socialAccount = SocialAccount.createSocialAccount(
                kakaoUserInfo.getProvider(),
                kakaoUserInfo.getProviderUid(),
                member
        );

        return socialAccountRepository.save(socialAccount);
    }

    private Member createMemberIfNeeded(KakaoUserInfo kakaoUserInfo) {

        if (kakaoUserInfo.getEmail() != null) {
            return memberRepository
                    .findByEmail(kakaoUserInfo.getEmail())
                    .orElseGet(() -> createNewMember(kakaoUserInfo));
        }

        return createNewMember(kakaoUserInfo);
    }

    private Member createNewMember(KakaoUserInfo kakaoUserInfo) {

        Member member = Member.createSocialMember(
                kakaoUserInfo.getEmail(),
                kakaoUserInfo.getNickname(),
                kakaoUserInfo.getProfileImageUrl()
        );

        return memberRepository.save(member);
    }
}
