package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.MemberLoginRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.mapper.KakaoAuthMapper;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberLoginUseCase {

    private final MemberService memberService;

    public KakaoLoginResponse execute(MemberLoginRequest request) {
        Member member = memberService.loginByLoginId(request.loginId(), request.password());
        return KakaoAuthMapper.toKakaoLoginResponse(member, false);
    }
}
