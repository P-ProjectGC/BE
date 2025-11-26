package plango.auth.application.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.dto.response.KakaoUserInfo;
import plango.member.domain.entity.Member;

/**
 * Kakao 인증 관련 Entity/DTO 매핑 담당
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoAuthMapper {

    /**
     * Kakao 사용자 정보 → Member 엔티티 매핑
     */
    public static Member toMember(KakaoUserInfo kakaoUserInfo) {
        return Member.createKakaoMember(
                kakaoUserInfo.getEmail(),
                kakaoUserInfo.getNickname(),
                kakaoUserInfo.getProfileImageUrl()
        );
    }

    /**
     * Member 엔티티 → KakaoLoginResponse DTO 매핑
     */
    public static KakaoLoginResponse toKakaoLoginResponse(Member member, boolean newMember) {
        return KakaoLoginResponse.of(member, newMember);
    }
}
