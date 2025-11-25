package plango.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class KakaoLoginResponse {

    private Long memberId;
    private String email;
    private String nickname;
    private String profileImageUrl;
}
