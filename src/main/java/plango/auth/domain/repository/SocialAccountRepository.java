package plango.auth.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import plango.auth.domain.entity.SocialAccount;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {

    Optional<SocialAccount> findByProviderAndProviderUserId(
            String provider,
            String providerUserId
    );

    List<SocialAccount> findByProviderAndProviderUserIdIn(
            String provider,
            Collection<String> providerUserIds
    );

    void deleteByMemberId(Long memberId);
}
