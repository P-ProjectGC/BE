package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.FindLoginIdRequest;
import plango.auth.application.dto.response.SendLoginIdVerificationCodeResponse;
import plango.auth.domain.service.EmailVerificationService;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;
import plango.member.exception.MemberErrorCode;
import plango.member.exception.MemberException;

@Service
@RequiredArgsConstructor
@Transactional
public class SendLoginIdVerificationCodeUseCase {

    private final MemberService memberService;

    private final EmailVerificationService emailVerificationService;

    public SendLoginIdVerificationCodeResponse execute(FindLoginIdRequest request) {
        Member member = memberService.findByEmail(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        emailVerificationService.createAndSendVerificationCode(member.getEmail());
        String maskedEmail = emailVerificationService.maskEmail(member.getEmail());

        return SendLoginIdVerificationCodeResponse.of(
                member.getEmail(),
                maskedEmail
        );
    }
}
