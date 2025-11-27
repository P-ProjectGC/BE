package plango.auth.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import plango.auth.application.dto.response.KakaoTokenResponse;
import plango.auth.application.dto.response.KakaoUserInfoResponse;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final RestTemplate restTemplate;

    @Value("${oauth2.kakao.client-id}")
    private String clientId;

    @Value("${oauth2.kakao.redirect-uri}")
    private String redirectUri;

    public KakaoUserInfoResponse getUserInfoByAuthorizationCode(String authorizationCode) {
        KakaoTokenResponse tokenResponse = getToken(authorizationCode);
        return fetchUserInfo(tokenResponse.accessToken());
    }

    private KakaoTokenResponse getToken(String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", authorizationCode);

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(params, headers);

        ResponseEntity<KakaoTokenResponse> response =
                restTemplate.postForEntity(TOKEN_URL, request, KakaoTokenResponse.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            log.warn("카카오 토큰 발급 실패. status={}, body={}",
                    response.getStatusCode(), response.getBody());

            ErrorCode errorCode = selectErrorCode(response.getStatusCode().value());
            throw new BusinessException(errorCode.getStatusCode(), errorCode.getMessage());
        }

        return response.getBody();
    }

    private KakaoUserInfoResponse fetchUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<KakaoUserInfoResponse> response =
                restTemplate.postForEntity(USER_INFO_URL, request, KakaoUserInfoResponse.class);

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            log.warn("카카오 사용자 정보 조회 실패. status={}, body={}",
                    response.getStatusCode(), response.getBody());

            ErrorCode errorCode = selectErrorCode(response.getStatusCode().value());
            throw new BusinessException(errorCode.getStatusCode(), errorCode.getMessage());
        }

        return response.getBody();
    }

    private ErrorCode selectErrorCode(int statusCode) {
        if (statusCode == 401) {
            return ErrorCode.KAKAO_INVALID_TOKEN;
        }
        return ErrorCode.KAKAO_SERVER_ERROR;
    }
}
