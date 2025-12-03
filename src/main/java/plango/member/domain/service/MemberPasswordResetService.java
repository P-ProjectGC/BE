package plango.member.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.member.domain.entity.LoginType;
import plango.member.domain.entity.Member;
import plango.member.domain.repository.MemberRepository;
import plango.member.exception.MemberErrorCode;
import plango.member.exception.MemberException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberPasswordResetService {

    private final MemberRepository memberRepository;

    public void validateLoginId(String loginId) {
        getNormalMemberByLoginId(loginId);
    }

    @Transactional
    public void resetPassword(
            String loginId,
            String newPassword,
            String newPasswordConfirm
    ) {
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new MemberException(MemberErrorCode.NEW_PASSWORD_CONFIRM_NOT_MATCH);
        }

        Member member = getNormalMemberByLoginId(loginId);

        member.changePassword(newPassword);
    }

    private Member getNormalMemberByLoginId(String loginId) {
        return memberRepository.findByLoginIdAndLoginType(loginId, LoginType.NORMAL)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
