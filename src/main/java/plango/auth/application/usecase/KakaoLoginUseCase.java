package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.KakaoLoginRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.dto.response.KakaoUserInfo;
import plango.auth.application.mapper.KakaoAuthMapper;
import plango.auth.domain.entity.SocialAccount;
import plango.auth.domain.repository.SocialAccountRepository;
import plango.auth.domain.service.KakaoAuthService;
import plango.member.domain.entity.Member;
import plango.member.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class KakaoLoginUseCase {

    private static final String KAKAO_PROVIDER = "KAKAO";

    private final KakaoAuthService kakaoAuthService;
    private final MemberRepository memberRepository;
    private final SocialAccountRepository socialAccountRepository;

    @Transactional
    public KakaoLoginResponse execute(KakaoLoginRequest request) {
        // 1) 인가 코드로 카카오 사용자 정보 조회
        KakaoUserInfo kakaoUserInfo =
                kakaoAuthService.getUserInfoByAuthorizationCode(request.authorizationCode());

        Long kakaoId = kakaoUserInfo.getId();

        // 2) 기존 소셜 계정 존재 여부 확인
        SocialAccount socialAccount = socialAccountRepository
                .findByProviderAndProviderUserId(KAKAO_PROVIDER, kakaoId.toString())
                .orElse(null);

        Member member;
        boolean isNewMember;

        if (socialAccount == null) {
            // 이메일 기준으로 기존 회원 여부 확인
            String email = kakaoUserInfo.getEmail();
            member = (email != null && !email.isBlank())
                    ? memberRepository.findByEmail(email).orElse(null)
                    : null;

            // 완전 신규 회원이면 생성
            if (member == null) {
                member = KakaoAuthMapper.toMember(kakaoUserInfo);
                memberRepository.save(member);
            }

            // 소셜 계정 연결
            socialAccount = SocialAccount.createKakaoAccount(kakaoId.toString(), member);
            socialAccountRepository.save(socialAccount);

            isNewMember = true;
        } else {
            // 이미 소셜 계정이 연결된 기존 회원
            member = socialAccount.getMember();
            isNewMember = false;
        }

        // 3) 응답 DTO 매핑 (추후 JWT 토큰 추가 가능)
        return KakaoAuthMapper.toKakaoLoginResponse(member, isNewMember);
    }
}
