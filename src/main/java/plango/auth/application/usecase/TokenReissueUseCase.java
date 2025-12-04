package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.TokenReissueRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.mapper.KakaoAuthMapper;
import plango.auth.domain.service.JwtTokenProvider;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenReissueUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    public KakaoLoginResponse execute(TokenReissueRequest request) {
        String refreshToken = request.refreshToken();

        Long memberId = jwtTokenProvider.getMemberIdFromRefreshToken(refreshToken);

        Member member = memberService.getById(memberId);

        String newAccessToken = jwtTokenProvider.createAccessToken(member.getId());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        return KakaoAuthMapper.toKakaoLoginResponse(
                member,
                false,
                newAccessToken,
                newRefreshToken
        );
    }
}
