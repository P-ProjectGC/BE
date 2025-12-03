package plango.member.domain.service;

import java.util.Collections;
import java.util.List;
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
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Optional<Member> findByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    public Member getByNickname(String nickname) {
        return memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public List<Member> searchByNicknameContaining(String keyword) {
        if (keyword == null) {
            return Collections.emptyList();
        }

        if (keyword.isBlank()) {
            return Collections.emptyList();
        }

        return memberRepository.findAllByNicknameContaining(keyword);
    }
}
