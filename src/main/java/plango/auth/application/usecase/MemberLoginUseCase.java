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
        // 비즈니스 로직은 Service에서 처리
        Member member = memberService.loginByLoginId(request.loginId(), request.password());

        // UseCase는 흐름 조합 + 응답 변환만 담당
        return KakaoAuthMapper.toKakaoLoginResponse(member, false);
    }
}
