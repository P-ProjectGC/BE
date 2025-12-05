package plango.global.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import plango.auth.application.exception.AuthException;
import plango.auth.domain.service.JwtTokenProvider;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null) {
            try {
                Long id = jwtTokenProvider.getMemberIdFromAccessToken(token);
                String role = jwtTokenProvider.getRoleFromAccessToken(token);

                List<GrantedAuthority> authorities;
                if ("ADMIN".equals(role)) {
                    authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
                } else {
                    // 일반 유저 토큰(role 없거나 다른 값) → ROLE_USER 부여
                    authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                id,
                                null,
                                authorities
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            } catch (AuthException exception) {
                log.warn("JWT 인증 실패: {}", exception.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (!StringUtils.hasText(bearerToken)) {
            return null;
        }

        if (!bearerToken.startsWith("Bearer ")) {
            return null;
        }

        return bearerToken.substring(7);
    }
}
