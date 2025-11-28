package plango.member.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plango.member.application.dto.response.MemberProfileResponse;
import plango.member.application.mapper.MemberMapper;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberGetService;

@Service
@RequiredArgsConstructor
public class GetMyProfileUseCase {

    private final MemberGetService memberGetService;
    private final MemberMapper memberMapper;

    public MemberProfileResponse execute(Long memberId) {
        Member member = memberGetService.getById(memberId);
        return memberMapper.toProfileResponse(member);
    }
}
