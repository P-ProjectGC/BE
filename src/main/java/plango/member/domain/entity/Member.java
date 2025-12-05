package plango.member.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(name = "login_id", unique = true, length = 50)
    private String loginId;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(length = 255)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false, length = 20)
    private LoginType loginType;

    public static Member createKakaoMember(
            String email,
            String nickname,
            String profileImageUrl
    ) {
        Member member = new Member();
        member.email = email;
        member.name = nickname;
        member.nickname = nickname;
        member.profileImageUrl = profileImageUrl;
        member.loginType = LoginType.KAKAO;
        return member;
    }

    public static Member createLocalMember(
            String name,
            String loginId,
            String email,
            String password,
            String nickname
    ) {
        Member member = new Member();
        member.name = name;
        member.loginId = loginId;
        member.email = email;
        member.password = password;
        member.nickname = nickname;
        member.loginType = LoginType.NORMAL;
        return member;
    }

    public void updateProfile(String nickname, String profileImageUrl) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
