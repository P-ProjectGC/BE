package plango.member.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plango.global.common.entity.BaseTimeEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 200)
    private String password;

    private String nickname;

    @Column(length = 255)
    private String profileImageUrl;

    // ===== 정적 팩토리 메서드 =====
    public static Member createKakaoMember(String email, String nickname, String profileImageUrl) {
        Member member = new Member();
        member.email = email;
        member.nickname = nickname;
        member.profileImageUrl = profileImageUrl;
        return member;
    }
}
