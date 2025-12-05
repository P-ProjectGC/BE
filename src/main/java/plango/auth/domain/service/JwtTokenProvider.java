package plango.auth.domain.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import plango.auth.application.exception.AuthErrorCode;
import plango.auth.application.exception.AuthException;

@Slf4j
@Service
public class JwtTokenProvider {

    private static final String CLAIM_TYPE = "type";
    private static final String CLAIM_ROLE = "role";
    private static final String TYPE_ACCESS = "ACCESS";
    private static final String TYPE_REFRESH = "REFRESH";

    private static final String ROLE_ADMIN = "ADMIN";

    private final Key secretKey;
    private final long accessTokenValidityInMillis;
    private final long refreshTokenValidityInMillis;

    public JwtTokenProvider(
            @Value("${security.jwt.secret-key}") String secretKey,
            @Value("${security.jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
            @Value("${security.jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMillis = accessTokenValidityInSeconds * 1000L;
        this.refreshTokenValidityInMillis = refreshTokenValidityInSeconds * 1000L;
    }

    public String createAccessToken(Long memberId) {
        return createToken(memberId, TYPE_ACCESS, accessTokenValidityInMillis);
    }

    public String createRefreshToken(Long memberId) {
        return createToken(memberId, TYPE_REFRESH, refreshTokenValidityInMillis);
    }

    public String getRoleFromAccessToken(String token) {
        Claims claims = parseAccessTokenClaims(token);
        return claims.get(CLAIM_ROLE, String.class);   // CLAIM_ROLE = "role"
    }

    private String createToken(
            Long memberId,
            String type,
            long validityInMillis
    ) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMillis);

        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .claim(CLAIM_TYPE, type)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createAdminAccessToken(Long adminId) {
        return createTokenWithRole(adminId, TYPE_ACCESS, ROLE_ADMIN, accessTokenValidityInMillis);
    }

    private String createTokenWithRole(
            Long id,
            String type,
            String role,
            long validityInMillis
    ) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMillis);

        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .claim(CLAIM_TYPE, type)
                .claim(CLAIM_ROLE, role)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getMemberIdFromAccessToken(String token) {
        Claims claims = parseAccessTokenClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    public Long getMemberIdFromRefreshToken(String token) {
        Claims claims = parseRefreshTokenClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    private Claims parseAccessTokenClaims(String token) {
        try {
            Claims claims = parseClaims(token);
            String type = claims.get(CLAIM_TYPE, String.class);

            if (!TYPE_ACCESS.equals(type)) {
                throw new AuthException(AuthErrorCode.INVALID_ACCESS_TOKEN);
            }

            return claims;
        } catch (ExpiredJwtException exception) {
            throw new AuthException(AuthErrorCode.EXPIRED_ACCESS_TOKEN);
        } catch (JwtException | IllegalArgumentException exception) {
            throw new AuthException(AuthErrorCode.INVALID_ACCESS_TOKEN);
        }
    }

    private Claims parseRefreshTokenClaims(String token) {
        try {
            Claims claims = parseClaims(token);
            String type = claims.get(CLAIM_TYPE, String.class);

            if (!TYPE_REFRESH.equals(type)) {
                throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
            }

            return claims;
        } catch (ExpiredJwtException exception) {
            throw new AuthException(AuthErrorCode.EXPIRED_REFRESH_TOKEN);
        } catch (JwtException | IllegalArgumentException exception) {
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
