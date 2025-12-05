package plango.admin.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plango.admin.application.dto.response.AdminMemberDetailResponse;
import plango.admin.application.mapper.AdminMemberMapper;
import plango.global.common.exception.BusinessException;
import plango.global.common.exception.ErrorCode;
import plango.member.domain.entity.Member;
import plango.member.domain.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AdminGetMemberDetailUseCase {

    private final MemberRepository memberRepository;

    public AdminMemberDetailResponse execute(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        return AdminMemberMapper.toDetailResponse(member);
    }
}
