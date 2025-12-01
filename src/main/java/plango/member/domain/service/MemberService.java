package plango.member.domain.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import plango.member.domain.entity.Member;
import plango.member.domain.repository.MemberRepository;
import plango.member.exception.MemberErrorCode;
import plango.member.exception.MemberException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public Optional<Member> findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    public void updateProfile(Long memberId, String nickname, String profileImageUrl) {
        Member member = getById(memberId);

        if (nickname != null && !nickname.equals(member.getNickname())) {
            validateNicknameUnique(nickname);
        }

        member.updateProfile(nickname, profileImageUrl);
    }

    @Transactional
    public void changePassword(Long memberId,
                               String currentPassword,
                               String newPassword,
                               String newPasswordConfirm) {

        Member member = getById(memberId);

        // 소셜(카카오) 회원은 비밀번호 변경 불가
        if (member.isSocialMember()) {
            throw new MemberException(MemberErrorCode.PASSWORD_CHANGE_NOT_ALLOWED_FOR_SOCIAL_MEMBER);
        }

        // 현재 비밀번호 검증
        if (!isPasswordMatch(member, currentPassword)) {
            throw new MemberException(MemberErrorCode.INVALID_CURRENT_PASSWORD);
        }

        // 새 비밀번호 & 확인 일치 여부 검증
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new MemberException(MemberErrorCode.NEW_PASSWORD_CONFIRM_NOT_MATCH);
        }

        // 이전 비밀번호와 동일한지 검증
        if (isPasswordMatch(member, newPassword)) {
            throw new MemberException(MemberErrorCode.NEW_PASSWORD_SAME_AS_OLD);
        }

        // 비밀번호 변경
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        member.updatePassword(encodedNewPassword);
    }

    public Member loginByLoginId(String loginId, String rawPassword) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.INVALID_MEMBER_CREDENTIAL));

        if (!isPasswordMatch(member, rawPassword)) {
            throw new MemberException(MemberErrorCode.INVALID_MEMBER_CREDENTIAL);
        }

        return member;
    }

    private void validateNicknameUnique(String nickname) {
        memberRepository.findByNickname(nickname)
                .ifPresent(existingMember -> {
                    throw new MemberException(MemberErrorCode.DUPLICATE_NICKNAME);
                });
    }

    private boolean isPasswordMatch(Member member, String rawPassword) {
        if (member.getPassword() == null || rawPassword == null) {
            return false;
        }

        return passwordEncoder.matches(rawPassword, member.getPassword());
    }
}
