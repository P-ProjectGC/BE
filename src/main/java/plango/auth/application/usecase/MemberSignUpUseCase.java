package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.MemberSignUpRequest;
import plango.auth.application.dto.response.MemberSignUpResponse;
import plango.member.application.mapper.MemberMapper;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;
import plango.member.exception.MemberErrorCode;
import plango.member.exception.MemberException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberSignUpUseCase {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public MemberSignUpResponse execute(MemberSignUpRequest request) {
        if (memberService.existsByLoginId(request.loginId())) {
            throw new MemberException(MemberErrorCode.DUPLICATE_LOGIN_ID);
        }

        if (memberService.existsByEmail(request.email())) {
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        if (memberService.existsByNickname(request.nickname())) {
            throw new MemberException(MemberErrorCode.DUPLICATE_NICKNAME);
        }

        Member member = memberMapper.toEntity(request);
        Member saved = memberService.save(member);

        return memberMapper.toSignUpResponse(saved);
    }
}
