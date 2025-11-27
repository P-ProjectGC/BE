package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.KakaoLoginRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.dto.response.KakaoUserInfoResponse;
import plango.auth.application.mapper.KakaoAuthMapper;
import plango.auth.domain.entity.SocialAccount;
import plango.auth.domain.service.KakaoAuthService;
import plango.auth.domain.service.SocialAccountService;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
public class KakaoLoginUseCase {

    private static final String KAKAO_PROVIDER = "KAKAO";

    private final KakaoAuthService kakaoAuthService;
    private final MemberService memberService;
    private final SocialAccountService socialAccountService;

    @Transactional
    public KakaoLoginResponse execute(KakaoLoginRequest request) {
        // 1) 인가 코드로 카카오 사용자 정보 조회
        KakaoUserInfoResponse kakaoUserInfo =
                kakaoAuthService.getUserInfoByAuthorizationCode(request.authorizationCode());

        Long kakaoId = kakaoUserInfo.getId();
        String email = kakaoUserInfo.getEmail();

        // 2) 기존 소셜 계정 조회 (서비스 사용)
        SocialAccount socialAccount = socialAccountService
                .findByProviderAndProviderUserId(KAKAO_PROVIDER, kakaoId.toString())
                .orElse(null);

        Member member;
        boolean isNewMember = false;

        if (socialAccount == null) {
            // 2-1) 기존 회원 조회 (서비스 사용)
            member = memberService.findByEmail(email)
                    .orElseGet(() -> memberService.save(KakaoAuthMapper.toMember(kakaoUserInfo)));

            // 2-2) 소셜 계정 생성 및 저장 (서비스 사용)
            socialAccount = socialAccountService.createAndSaveKakaoAccount(
                    kakaoId.toString(),
                    member
            );
            isNewMember = true;
        } else {
            // 기존 회원
            member = socialAccount.getMember();
        }

        // 3) 응답 DTO 매핑
        return KakaoAuthMapper.toKakaoLoginResponse(member, isNewMember);
    }
}
