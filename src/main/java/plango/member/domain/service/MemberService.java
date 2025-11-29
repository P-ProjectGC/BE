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

        return member.getPassword().equals(rawPassword);
    }
}
