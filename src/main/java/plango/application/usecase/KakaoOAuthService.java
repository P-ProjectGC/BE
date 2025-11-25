package plango.application.usecase;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import plango.application.dto.response.KakaoUserInfo;
import plango.domain.entity.SocialProvider;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;

@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public KakaoUserInfo getUserInfo(String kakaoAccessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(kakaoAccessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_USER_INFO_URL,
                HttpMethod.GET,
                httpEntity,
                String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new BusinessException(ErrorCode.JSON_PARSE_EXCEPTION);
        }

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            long id = root.get("id").asLong();

            JsonNode kakaoAccount = root.get("kakao_account");
            String email = kakaoAccount.has("email") ? kakaoAccount.get("email").asText() : null;

            JsonNode profile = kakaoAccount.get("profile");
            String nickname = profile.has("nickname") ? profile.get("nickname").asText() : null;
            String profileImageUrl = profile.has("profile_image_url")
                    ? profile.get("profile_image_url").asText()
                    : null;

            return new KakaoUserInfo(
                    String.valueOf(id),
                    email,
                    nickname,
                    profileImageUrl,
                    SocialProvider.KAKAO
            );

        } catch (Exception exception) {
            throw new BusinessException(ErrorCode.JSON_PARSE_EXCEPTION);
        }
    }
}
