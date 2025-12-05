package plango.auth.domain.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import plango.auth.application.dto.response.KakaoTokenResponse;
import plango.auth.application.dto.response.KakaoUserInfoResponse;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAuthService {

    private static final String KAKAO_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_USER_INFO_URI = "https://kapi.kakao.com/v2/user/me";

    @Value("${security.oauth2.kakao.client-id}")
    private String clientId;

    @Value("${security.oauth2.kakao.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate;

    @PostConstruct
    public void printConfig() {
        log.info("KakaoAuthService initialized.");
        log.info("clientId = {}", clientId);
        log.info("redirectUri = {}", redirectUri);
    }

    /**
     * (웹용) 인가 코드를 이용해 카카오 사용자 정보를 조회하는 메서드
     */
    public KakaoUserInfoResponse getUserInfoByAuthorizationCode(String authorizationCode) {
        KakaoTokenResponse tokenResponse = requestToken(authorizationCode);
        String accessToken = tokenResponse.accessToken();
        log.info("accessToken from Kakao = {}", accessToken);
        return requestUserInfo(accessToken);
    }

    /**
     * (모바일용) 이미 발급받은 accessToken 으로 바로 사용자 정보 조회
     */
    public KakaoUserInfoResponse getUserInfoByAccessToken(String accessToken) {
        return requestUserInfo(accessToken);
    }

    private KakaoTokenResponse requestToken(String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", authorizationCode);

        log.info("requestToken() body = {}", body);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<KakaoTokenResponse> response = restTemplate.postForEntity(
                    KAKAO_TOKEN_URI,
                    entity,
                    KakaoTokenResponse.class
            );

            log.info("requestToken() status = {}", response.getStatusCode());

            KakaoTokenResponse tokenResponse = response.getBody();

            if (!response.getStatusCode().is2xxSuccessful() || tokenResponse == null) {
                throw new BusinessException(
                        ErrorCode.KAKAO_SERVER_ERROR.getStatusCode(),
                        ErrorCode.KAKAO_SERVER_ERROR.getMessage()
                );
            }

            if (tokenResponse.accessToken() == null || tokenResponse.accessToken().isBlank()) {
                log.error("Kakao token response has no access_token. body = {}", tokenResponse);
                throw new BusinessException(
                        ErrorCode.KAKAO_SERVER_ERROR.getStatusCode(),
                        ErrorCode.KAKAO_SERVER_ERROR.getMessage()
                );
            }

            return tokenResponse;
        } catch (RestClientResponseException exception) {
            log.error(
                    "Kakao token request error. status = {}, body = {}",
                    exception.getRawStatusCode(),
                    exception.getResponseBodyAsString()
            );

            ErrorCode errorCode = selectErrorCode(exception.getRawStatusCode());

            throw new BusinessException(
                    errorCode.getStatusCode(),
                    errorCode.getMessage()
            );
        } catch (RestClientException exception) {
            log.error("Kakao token request RestClientException", exception);

            throw new BusinessException(
                    ErrorCode.KAKAO_SERVER_ERROR.getStatusCode(),
                    ErrorCode.KAKAO_SERVER_ERROR.getMessage()
            );
        }
    }

    private KakaoUserInfoResponse requestUserInfo(String accessToken) {
        HttpHeaders headers = createBearerHeaders(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoUserInfoResponse> response = restTemplate.exchange(
                    KAKAO_USER_INFO_URI,
                    HttpMethod.GET,
                    entity,
                    KakaoUserInfoResponse.class
            );

            log.info("requestUserInfo() status = {}", response.getStatusCode());

            KakaoUserInfoResponse userInfo = response.getBody();

            if (!response.getStatusCode().is2xxSuccessful() || userInfo == null) {
                throw new BusinessException(
                        ErrorCode.KAKAO_SERVER_ERROR.getStatusCode(),
                        ErrorCode.KAKAO_SERVER_ERROR.getMessage()
                );
            }

            return userInfo;
        } catch (RestClientResponseException exception) {
            log.error(
                    "Kakao userInfo request error. status = {}, body = {}",
                    exception.getRawStatusCode(),
                    exception.getResponseBodyAsString()
            );

            ErrorCode errorCode = selectErrorCode(exception.getRawStatusCode());

            throw new BusinessException(
                    errorCode.getStatusCode(),
                    errorCode.getMessage()
            );
        } catch (RestClientException exception) {
            log.error("Kakao userInfo request RestClientException", exception);

            throw new BusinessException(
                    ErrorCode.KAKAO_SERVER_ERROR.getStatusCode(),
                    ErrorCode.KAKAO_SERVER_ERROR.getMessage()
            );
        }
    }

    private HttpHeaders createBearerHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        return headers;
    }

    private ErrorCode selectErrorCode(int statusCode) {
        if (statusCode == 401) {
            return ErrorCode.KAKAO_INVALID_TOKEN;
        }

        return ErrorCode.KAKAO_SERVER_ERROR;
    }
}

