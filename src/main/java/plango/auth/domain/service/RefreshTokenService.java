package plango.auth.domain.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.exception.AuthErrorCode;
import plango.auth.application.exception.AuthException;
import plango.auth.domain.entity.RefreshToken;
import plango.auth.domain.repository.RefreshTokenRepository;
import plango.member.domain.entity.Member;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveOrUpdate(Member member, String token) {
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByMember_Id(member.getId());

        if (optionalRefreshToken.isPresent()) {
            RefreshToken refreshToken = optionalRefreshToken.get();
            refreshToken.updateToken(token);
            return;
        }

        RefreshToken refreshToken = RefreshToken.create(member, token);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public RefreshToken getByMemberId(Long memberId) {
        return refreshTokenRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN));
    }

    @Transactional
    public void deleteByMemberId(Long memberId) {
        refreshTokenRepository.deleteByMember_Id(memberId);
    }
}
