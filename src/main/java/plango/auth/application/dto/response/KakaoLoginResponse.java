package plango.auth.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plango.member.domain.entity.Member;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoLoginResponse {

    private Long memberId;
    private String email;
    private String nickname;
    private String profileImageUrl;
    private boolean newMember;

    public static KakaoLoginResponse of(Member member, boolean newMember) {
        return KakaoLoginResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .newMember(newMember)
                .build();
    }
}
