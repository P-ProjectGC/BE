package plango.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import plango.global.common.entity.BaseTimeEntity;

@Getter
@Entity
@Table(name = "social_account")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialAccount extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SocialProvider provider;

    @Column(nullable = false, unique = true, length = 100)
    private String providerUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static SocialAccount createSocialAccount(
            SocialProvider provider,
            String providerUid,
            Member member
    ) {
        return SocialAccount.builder()
                .provider(provider)
                .providerUid(providerUid)
                .member(member)
                .build();
    }
}
