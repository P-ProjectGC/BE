package plango.auth.application.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.dto.response.KakaoUserInfoResponse;
import plango.member.domain.entity.Member;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoAuthMapper {

    public static Member toMember(KakaoUserInfoResponse kakaoUserInfo) {
        return Member.createKakaoMember(
                kakaoUserInfo.getEmail(),
                kakaoUserInfo.getNickname(),
                kakaoUserInfo.getProfileImageUrl()
        );
    }

    public static KakaoLoginResponse toKakaoLoginResponse(Member member, boolean newMember) {
        return KakaoLoginResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .newMember(newMember)
                .build();
    }
}
