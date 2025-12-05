package plango.admin.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plango.admin.application.dto.response.AdminMemberSummaryResponse;
import plango.admin.application.mapper.AdminMemberMapper;
import plango.member.domain.entity.Member;
import plango.member.domain.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminSearchMembersUseCase {

    private final MemberRepository memberRepository;

    public List<AdminMemberSummaryResponse> execute(String keyword) {
        List<Member> members;

        if (keyword == null || keyword.isBlank()) {
            members = memberRepository.findAll();
        } else {
            members = memberRepository.findByLoginIdContainingOrEmailContainingOrNicknameContaining(
                    keyword,
                    keyword,
                    keyword
            );
        }

        return AdminMemberMapper.toSummaryResponses(members);
    }
}