package plango.auth.domain.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.member.domain.entity.Member;
import plango.auth.domain.entity.SocialAccount;
import plango.member.domain.repository.MemberRepository;
import plango.auth.domain.repository.SocialAccountRepository;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private static final String KAKAO_PROVIDER = "KAKAO";
    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;
    private final SocialAccountRepository socialAccountRepository;

    public KakaoLoginResponse login(String accessToken) {
        KakaoUserResponse kakaoUserResponse = requestKakaoUser(accessToken);

        Long kakaoId = kakaoUserResponse.getId();
        KakaoUserResponse.KakaoAccount account = kakaoUserResponse.getKakaoAccount();

        String email = account != null ? account.getEmail() : null;
        String nickname = account != null && account.getProfile() != null
                ? account.getProfile().getNickname()
                : "카카오사용자";
        String profileImageUrl = account != null && account.getProfile() != null
                ? account.getProfile().getProfileImageUrl()
                : null;

        String providerUid = String.valueOf(kakaoId);

        SocialAccount socialAccount = socialAccountRepository
                .findByProviderAndProviderUid(KAKAO_PROVIDER, providerUid)
                .orElseGet(() -> createMemberAndSocialAccount(providerUid, email, nickname, profileImageUrl));

        Member member = socialAccount.getMember();

        return KakaoLoginResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }

    private KakaoUserResponse requestKakaoUser(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoUserResponse> response = restTemplate.exchange(
                    KAKAO_USER_INFO_URL,
                    HttpMethod.GET,
                    entity,
                    KakaoUserResponse.class
            );

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new BusinessException(
                        ErrorCode.KAKAO_INVALID_TOKEN.getStatusCode(),
                        ErrorCode.KAKAO_INVALID_TOKEN.getMessage()
                );
            }

            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new BusinessException(
                    ErrorCode.KAKAO_INVALID_TOKEN.getStatusCode(),
                    ErrorCode.KAKAO_INVALID_TOKEN.getMessage()
            );
        } catch (RestClientException e) {
            throw new BusinessException(
                    ErrorCode.KAKAO_SERVER_ERROR.getStatusCode(),
                    ErrorCode.KAKAO_SERVER_ERROR.getMessage()
            );
        }
    }

    private SocialAccount createMemberAndSocialAccount(
            String providerUid,
            String email,
            String nickname,
            String profileImageUrl
    ) {
        Member member = Member.builder()
                .email(email)
                .password(null)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();

        Member savedMember = memberRepository.save(member);

        SocialAccount socialAccount = SocialAccount.builder()
                .provider(KAKAO_PROVIDER)
                .providerUid(providerUid)
                .member(savedMember)
                .build();

        return socialAccountRepository.save(socialAccount);
    }

    @Getter
    @Setter
    private static class KakaoUserResponse {

        private Long id;

        @JsonProperty("kakao_account")
        private KakaoAccount kakaoAccount;

        @Getter
        @Setter
        private static class KakaoAccount {

            private String email;
            private Profile profile;
        }

        @Getter
        @Setter
        private static class Profile {

            private String nickname;

            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }
}
