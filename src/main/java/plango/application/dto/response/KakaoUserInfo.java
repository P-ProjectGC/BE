package plango.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import plango.domain.entity.SocialProvider;

@Getter
@AllArgsConstructor
public class KakaoUserInfo {

    private String providerUid;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private SocialProvider provider;
}
