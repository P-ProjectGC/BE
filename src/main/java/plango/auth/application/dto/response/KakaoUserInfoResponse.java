package plango.auth.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoUserInfoResponse(
        Long id,
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {

    public String getEmail() {
        if (kakaoAccount == null) {
            return null;
        }

        return kakaoAccount.getEmail();
    }

    public String getNickname() {
        if (kakaoAccount == null) {
            return null;
        }

        if (kakaoAccount.getProfile() == null) {
            return null;
        }

        return kakaoAccount.getProfile().getNickname();
    }

    public String getProfileImageUrl() {
        if (kakaoAccount == null) {
            return null;
        }

        if (kakaoAccount.getProfile() == null) {
            return null;
        }

        return kakaoAccount.getProfile().getProfileImageUrl();
    }

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KakaoAccount {

        private String email;

        @JsonProperty("profile")
        private Profile profile;
    }

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Profile {

        @JsonProperty("nickname")
        private String nickname;

        @JsonProperty("profile_image_url")
        private String profileImageUrl;
    }
}
