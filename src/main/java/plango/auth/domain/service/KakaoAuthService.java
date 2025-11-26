package plango.auth.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import plango.auth.application.dto.response.KakaoTokenResponse;
import plango.auth.application.dto.response.KakaoUserInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private final RestTemplate restTemplate;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.client-secret:}")
    private String clientSecret;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    /**
     * 인가 코드로 카카오 access token 발급 후, 해당 토큰으로 유저 정보 조회
     */
    public KakaoUserInfo getUserInfoByAuthorizationCode(String authorizationCode) {
        KakaoTokenResponse tokenResponse = fetchAccessToken(authorizationCode);

        String accessToken = tokenResponse.getAccessToken();
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalStateException("카카오 access token 발급에 실패했습니다.");
        }

        return fetchUserInfo(accessToken);
    }

    private KakaoTokenResponse fetchAccessToken(String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", authorizationCode);
        if (clientSecret != null && !clientSecret.isBlank()) {
            params.add("client_secret", clientSecret);
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<KakaoTokenResponse> response =
                restTemplate.postForEntity(TOKEN_URL, request, KakaoTokenResponse.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            log.warn("카카오 토큰 발급 실패. status={}, body={}",
                    response.getStatusCode(), response.getBody());
            throw new IllegalStateException("카카오 토큰 발급 요청에 실패했습니다.");
        }

        return response.getBody();
    }

    private KakaoUserInfo fetchUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserInfo> response =
                restTemplate.postForEntity(USER_INFO_URL, request, KakaoUserInfo.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            log.warn("카카오 사용자 정보 조회 실패. status={}, body={}",
                    response.getStatusCode(), response.getBody());
            throw new IllegalStateException("카카오 사용자 정보 조회에 실패했습니다.");
        }

        return response.getBody();
    }
}
