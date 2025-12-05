package plango.admin.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.admin.application.dto.request.AdminMemberUpdateRequest;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;
import plango.member.domain.entity.Member;
import plango.member.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminUpdateMemberUseCase {

    private final MemberRepository memberRepository;

    public void execute(Long memberId, AdminMemberUpdateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        if (request.email() != null && !request.email().isBlank()) {
            member.updateEmail(request.email());
        }

        if (request.nickname() != null && !request.nickname().isBlank()) {
            member.updateNickname(request.nickname());
        }
    }
}
