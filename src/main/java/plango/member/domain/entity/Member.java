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

    // 일반 로그인용 아이디 (로그인 시 사용)
    @Column(name = "login_id", nullable = false, unique = true, length = 50)
    private String loginId;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(length = 255)
    private String profileImageUrl;

    // ===== 정적 팩토리 메서드 =====
    // 카카오 로그인으로 가입하는 회원 생성 시 사용
    public static Member createKakaoMember(String email, String nickname, String profileImageUrl) {
        Member member = new Member();
        // 카카오 회원은 우선 loginId 를 email 과 동일하게 사용
        member.loginId = email;
        member.email = email;
        member.nickname = nickname;
        member.profileImageUrl = profileImageUrl;
        // 비밀번호는 아직 없음 (추후 일반 로그인 전환 시 별도 처리)
        return member;
    }
}
