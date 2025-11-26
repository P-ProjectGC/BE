package plango.auth.domain.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.domain.entity.SocialAccount;
import plango.auth.domain.repository.SocialAccountRepository;
import plango.member.domain.entity.Member;

@Service
@RequiredArgsConstructor
public class SocialAccountService {

    private final SocialAccountRepository socialAccountRepository;

    @Transactional(readOnly = true)
    public Optional<SocialAccount> findByProviderAndProviderUserId(
            String provider,
            String providerUserId
    ) {
        return socialAccountRepository.findByProviderAndProviderUserId(provider, providerUserId);
    }

    @Transactional
    public SocialAccount createAndSaveKakaoAccount(String providerUserId, Member member) {
        SocialAccount socialAccount = SocialAccount.createKakaoAccount(providerUserId, member);
        return socialAccountRepository.save(socialAccount);
    }
}
