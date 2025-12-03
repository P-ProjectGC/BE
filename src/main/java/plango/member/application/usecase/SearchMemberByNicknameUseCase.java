package plango.member.application.usecase;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.member.application.dto.response.MemberSearchResponse;
import plango.member.application.mapper.MemberSearchMapper;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberQueryService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchMemberByNicknameUseCase {

    private final MemberQueryService memberQueryService;

    private final MemberSearchMapper memberSearchMapper;

    public List<MemberSearchResponse> execute(String nickname) {
        List<Member> members = memberQueryService.searchByNicknameContaining(nickname);

        return members.stream()
                .map(memberSearchMapper::toSearchResponse)
                .collect(Collectors.toList());
    }
}
