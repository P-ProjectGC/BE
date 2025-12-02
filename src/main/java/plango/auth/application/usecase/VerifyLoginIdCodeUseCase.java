package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.VerifyLoginIdCodeRequest;
import plango.auth.application.dto.response.FindLoginIdResultResponse;
import plango.auth.domain.service.EmailVerificationService;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;
import plango.member.exception.MemberErrorCode;
import plango.member.exception.MemberException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VerifyLoginIdCodeUseCase {

    private final MemberService memberService;

    private final EmailVerificationService emailVerificationService;

    public FindLoginIdResultResponse execute(VerifyLoginIdCodeRequest request) {
        emailVerificationService.verifyCode(request.email(), request.code());

        Member member = memberService.findByEmail(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        return FindLoginIdResultResponse.from(member.getLoginId());
    }
}
