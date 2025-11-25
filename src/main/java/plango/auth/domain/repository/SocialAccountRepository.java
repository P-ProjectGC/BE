package plango.auth.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import plango.auth.domain.entity.SocialAccount;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {

    Optional<SocialAccount> findByProviderAndProviderUid(String provider, String providerUid);
}
