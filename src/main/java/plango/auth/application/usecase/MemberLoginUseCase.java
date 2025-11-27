package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.MemberLoginRequest;
import plango.auth.application.dto.response.KakaoLoginResponse;
import plango.auth.application.mapper.KakaoAuthMapper;
import plango.global.common.exception.BusinessException;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
public class MemberLoginUseCase {

    private final MemberService memberService;

    @Transactional(readOnly = true)
    public KakaoLoginResponse execute(MemberLoginRequest request) {
        Member member = memberService.findByEmail(request.loginId())
                .orElseThrow(() -> new BusinessException(
                        HttpStatus.UNAUTHORIZED.value(),
                        "아이디 또는 비밀번호가 일치하지 않습니다."
                ));

        if (!member.getPassword().equals(request.password())) {
            throw new BusinessException(
                    HttpStatus.UNAUTHORIZED.value(),
                    "아이디 또는 비밀번호가 일치하지 않습니다."
            );
        }

        // 일반 로그인은 항상 기존 회원 → newMember = false
        return KakaoAuthMapper.toKakaoLoginResponse(member, false);
    }
}
