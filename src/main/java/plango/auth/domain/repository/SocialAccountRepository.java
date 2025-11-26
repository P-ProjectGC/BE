package plango.auth.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plango.auth.domain.entity.SocialAccount;

import java.util.Optional;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {

    Optional<SocialAccount> findByProviderAndProviderUserId(String provider, String providerUserId);
}
