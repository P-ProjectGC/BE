package plango.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import plango.domain.entity.SocialAccount;
import plango.domain.entity.SocialProvider;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {

    Optional<SocialAccount> findByProviderAndProviderUid(
            SocialProvider provider,
            String providerUid
    );
}
