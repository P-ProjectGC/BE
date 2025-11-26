package plango.auth.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfo {

    /**
     * 카카오 유저 고유 ID
     */
    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {

        private String email;
        private Profile profile;
    }

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Profile {

        private String nickname;

        @JsonProperty("profile_image_url")
        private String profileImageUrl;
    }

    // 편의 메서드들
    public String getEmail() {
        return kakaoAccount != null ? kakaoAccount.getEmail() : null;
    }

    public String getNickname() {
        return (kakaoAccount != null && kakaoAccount.getProfile() != null)
                ? kakaoAccount.getProfile().getNickname()
                : null;
    }

    public String getProfileImageUrl() {
        return (kakaoAccount != null && kakaoAccount.getProfile() != null)
                ? kakaoAccount.getProfile().getProfileImageUrl()
                : null;
    }
}
