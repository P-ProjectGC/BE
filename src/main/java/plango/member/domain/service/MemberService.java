package plango.member.domain.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.member.domain.entity.Member;
import plango.member.domain.repository.MemberRepository;
import plango.member.exception.MemberErrorCode;
import plango.member.exception.MemberException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getById(Long memberId) {
        return memberRepository
                .findById(memberId)
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

    public boolean existsByLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean existsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    public Member loginByLoginId(String loginId, String password) {
        Member member = memberRepository
                .findByLoginId(loginId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.INVALID_MEMBER_CREDENTIAL));

        if (!member.getPassword().equals(password)) {
            throw new MemberException(MemberErrorCode.INVALID_MEMBER_CREDENTIAL);
        }

        return member;
    }

    @Transactional
    public void changePassword(
            Long memberId,
            String currentPassword,
            String newPassword,
            String newPasswordConfirm
    ) {
        Member member = getById(memberId);

        if (!member.getPassword().equals(currentPassword)) {
            throw new MemberException(MemberErrorCode.INVALID_MEMBER_CREDENTIAL);
        }

        if (!newPassword.equals(newPasswordConfirm)) {
            throw new MemberException(MemberErrorCode.INVALID_MEMBER_CREDENTIAL);
        }

        member.changePassword(newPassword);
    }

    @Transactional
    public void updateProfile(
            Long memberId,
            String nickname,
            String profileImageUrl
    ) {
        Member member = getById(memberId);
        member.updateProfile(nickname, profileImageUrl);
    }
}
