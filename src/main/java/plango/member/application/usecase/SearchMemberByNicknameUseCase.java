package plango.member.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plango.member.application.dto.response.MemberSearchResponse;
import plango.member.application.mapper.MemberSearchMapper;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberQueryService;

@Service
@RequiredArgsConstructor
public class SearchMemberByNicknameUseCase {

    private final MemberQueryService memberQueryService;
    private final MemberSearchMapper memberSearchMapper;

    public MemberSearchResponse execute(String nickname) {
        Member member = memberQueryService.getByNickname(nickname);
        return memberSearchMapper.toSearchResponse(member);
    }
}
