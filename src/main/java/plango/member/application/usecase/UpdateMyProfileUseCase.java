package plango.member.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plango.member.application.dto.request.MemberProfileUpdateRequest;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberGetService;

@Service
@RequiredArgsConstructor
public class UpdateMyProfileUseCase {

    private final MemberGetService memberGetService;

    public void execute(Long memberId, MemberProfileUpdateRequest request) {
        Member member = memberGetService.getById(memberId);
        member.updateProfile(
                request.name(),
                request.nickname(),
                request.profileImageUrl()
        );
    }
}
