package plango.admin.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.domain.service.SocialAccountService;
import plango.friend.domain.service.FriendService;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberGetService;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
public class AdminDeleteMemberUseCase {

    private final MemberGetService memberGetService;

    private final MemberService memberService;

    private final SocialAccountService socialAccountService;

    private final FriendService friendService;

    @Transactional
    public void execute(Long memberId) {
        Member member = memberGetService.getById(memberId);

        friendService.deleteAllByMember(member);

        socialAccountService.deleteByMemberId(member.getId());

        memberService.deleteById(member.getId());
    }
}
