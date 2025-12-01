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
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
public class KakaoLoginUseCase {

    private static final String PROVIDER_KAKAO = "KAKAO";

    private final KakaoAuthService kakaoAuthService;
    private final SocialAccountService socialAccountService;
    private final MemberService memberService;

    @Transactional
    public KakaoLoginResponse execute(KakaoLoginRequest request) {
        KakaoUserInfoResponse kakaoUserInfo =
                kakaoAuthService.getUserInfoByAuthorizationCode(request.authorizationCode());

        Long kakaoId = kakaoUserInfo.id();

        if (kakaoId == null) {
            throw new BusinessException(ErrorCode.KAKAO_SERVER_ERROR);
        }

        SocialAccount socialAccount =
                socialAccountService.findByProviderAndProviderUserId(
                        PROVIDER_KAKAO,
                        String.valueOf(kakaoId)
                ).orElse(null);

        Member member;
        boolean newMember;

        if (socialAccount == null) {
            Member created = KakaoAuthMapper.toMember(kakaoUserInfo);
            member = memberService.save(created);
            socialAccountService.createAndSaveKakaoAccount(
                    String.valueOf(kakaoId),
                    member
            );
            newMember = true;
        } else {
            member = socialAccount.getMember();
            newMember = false;
        }

        return KakaoAuthMapper.toKakaoLoginResponse(member, newMember);
    }
}
