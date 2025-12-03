package plango.auth.application.mapper;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.dto.response.KakaoUserInfoResponse;
import plango.member.domain.entity.Member;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoAuthMapper {

    private static String createTempNickname() {
        String uuid = UUID.randomUUID()
                .toString()
                .replace("-", "");

        return "KAKAO_TMP_" + uuid.substring(0, 12);
    }

    private static String createRandomToken() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "");
    }

    public static Member toMember(KakaoUserInfoResponse kakaoUserInfo) {
        String email = kakaoUserInfo.getEmail();
        String profileImageUrl = kakaoUserInfo.getProfileImageUrl();
        String tempNickname = createTempNickname();

        return Member.createKakaoMember(
                email,
                tempNickname,
                profileImageUrl
        );
    }

    public static KakaoLoginResponse toKakaoLoginResponse(
            Member member,
            boolean newMember
    ) {
        String nickname = null;

        if (!newMember) {
            nickname = member.getNickname();
        }

        String accessToken = createRandomToken();
        String refreshToken = createRandomToken();

        return KakaoLoginResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(nickname)
                .profileImageUrl(member.getProfileImageUrl())
                .newMember(newMember)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
