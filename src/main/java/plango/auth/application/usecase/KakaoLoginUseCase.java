package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.KakaoLoginRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.dto.response.KakaoUserInfo;
import plango.auth.domain.entity.SocialAccount;
import plango.auth.domain.repository.SocialAccountRepository;
import plango.auth.domain.service.KakaoAuthService;
import plango.member.domain.entity.Member;
import plango.member.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class KakaoLoginUseCase {

    private final KakaoAuthService kakaoAuthService;
    private final MemberRepository memberRepository;
    private final SocialAccountRepository socialAccountRepository;

    @Transactional
    public KakaoLoginResponse execute(KakaoLoginRequest request) {

        // 1) 인가 코드 → 카카오 유저 정보 조회
        KakaoUserInfo kakaoUserInfo =
                kakaoAuthService.getUserInfoByAuthorizationCode(request.getAuthorizationCode());

        Long kakaoId = kakaoUserInfo.getId();
        String email = kakaoUserInfo.getEmail();
        String nickname = kakaoUserInfo.getNickname();
        String profileImageUrl = kakaoUserInfo.getProfileImageUrl();

        // 2) 소셜 계정 존재 여부 확인
        SocialAccount socialAccount =
                socialAccountRepository.findByProviderAndProviderUserId("KAKAO", kakaoId.toString())
                        .orElse(null);

        boolean isNewMember = false;
        Member member;

        if (socialAccount == null) {
            // 처음 로그인하는 카카오 계정 → 회원 + 소셜 계정 생성
            member = Member.createKakaoMember(email, nickname, profileImageUrl);
            memberRepository.save(member);

            socialAccount = SocialAccount.createKakaoAccount(kakaoId.toString(), member);
            socialAccountRepository.save(socialAccount);

            isNewMember = true;
        } else {
            // 기존 회원
            member = socialAccount.getMember();
        }

        // 3) 응답 DTO 생성 (필요하면 JWT 로직 추가)
        return KakaoLoginResponse.of(member, isNewMember);
    }
}
