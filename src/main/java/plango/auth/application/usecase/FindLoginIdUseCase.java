package plango.auth.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plango.auth.application.dto.request.FindLoginIdRequest;
import plango.auth.application.dto.response.FindLoginIdPreviewResponse;
import plango.member.domain.entity.Member;
import plango.member.domain.service.MemberService;
import plango.member.exception.MemberErrorCode;
import plango.member.exception.MemberException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FindLoginIdUseCase {

    private static final int MASK_SIZE = 5;

    private final MemberService memberService;

    public FindLoginIdPreviewResponse execute(FindLoginIdRequest request) {
        Member member = memberService.findByEmail(request.email())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        String maskedLoginId = maskLoginId(member.getLoginId());

        return FindLoginIdPreviewResponse.of(request.email(), maskedLoginId);
    }

    private String maskLoginId(String loginId) {
        if (loginId == null || loginId.isBlank()) {
            return "";
        }

        if (loginId.length() <= MASK_SIZE) {
            return "*".repeat(loginId.length());
        }

        int visibleLength = loginId.length() - MASK_SIZE;

        String visible = loginId.substring(0, visibleLength);
        String masked = "*".repeat(MASK_SIZE);

        return visible + masked;
    }
}
