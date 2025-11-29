package plango.member.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plango.member.application.dto.request.MemberProfileUpdateRequest;
import plango.member.domain.service.MemberService;

@Service
@RequiredArgsConstructor
public class UpdateMyProfileUseCase {

    private final MemberService memberService;

    public void execute(Long memberId, MemberProfileUpdateRequest request) {
        memberService.updateProfile(
                memberId,
                request.nickname(),
                request.profileImageUrl()
        );
    }
}
