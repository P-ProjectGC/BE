package plango.member.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plango.global.common.entity.BaseTimeEntity;
import plango.member.domain.entity.LoginType;

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

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(length = 255)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LoginType loginType;

    public static Member createKakaoMember(
            String email,
            String nickname,
            String profileImageUrl
    ) {
        Member member = new Member();
        member.email = email;
        member.nickname = nickname;
        member.profileImageUrl = profileImageUrl;
        member.loginType = LoginType.KAKAO;
        return member;
    }

    public void updateProfile(String nickname, String profileImageUrl) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static Member createNormalMember(
            String loginId,
            String email,
            String encodedPassword,
            String nickname
    ) {
        Member member = new Member();
        member.loginId = loginId;
        member.email = email;
        member.password = encodedPassword;
        member.nickname = nickname;
        member.loginType = LoginType.NORMAL;
        return member;
    }

    public boolean isSocialMember() {
        return this.loginType == LoginType.KAKAO;
    }

    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
