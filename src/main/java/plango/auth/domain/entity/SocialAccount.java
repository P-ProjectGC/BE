package plango.auth.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plango.global.common.entity.BaseTimeEntity;
import plango.member.domain.entity.Member;

@Getter
@Entity
@Table(name = "social_account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialAccount extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 소셜 로그인 제공자 (예: KAKAO, GOOGLE 등)
     */
    @Column(nullable = false, length = 20)
    private String provider;

    /**
     * 제공자 쪽의 고유 사용자 ID
     *  - DB 컬럼명: provider_uid
     */
    @Column(name = "provider_uid", nullable = false, length = 100)
    private String providerUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // ===== 연관관계 편의 메서드 =====
    private void setMember(Member member) {
        this.member = member;
    }

    // ===== 정적 팩토리 메서드 =====
    public static SocialAccount createKakaoAccount(String providerUserId, Member member) {
        SocialAccount socialAccount = new SocialAccount();
        socialAccount.provider = "KAKAO";
        socialAccount.providerUserId = providerUserId;
        socialAccount.setMember(member);
        return socialAccount;
    }
}
