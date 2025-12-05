package plango.admin.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plango.admin.application.dto.request.AdminMemberUpdateRequest;
import plango.admin.application.dto.response.AdminMemberDetailResponse;
import plango.admin.application.dto.response.AdminMemberSummaryResponse;
import plango.admin.application.usecase.AdminGetMemberDetailUseCase;
import plango.admin.application.usecase.AdminSearchMembersUseCase;
import plango.admin.application.usecase.AdminUpdateMemberUseCase;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;

import java.util.List;

@Tag(
        name = "관리자 회원 관리",
        description = "관리자 화면에서 회원을 조회하고 수정하는 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class AdminMemberController {

    private final AdminSearchMembersUseCase adminSearchMembersUseCase;

    private final AdminGetMemberDetailUseCase adminGetMemberDetailUseCase;

    private final AdminUpdateMemberUseCase adminUpdateMemberUseCase;

    @Operation(
            summary = "회원 목록 조회",
            description = "키워드(로그인 아이디, 이메일, 닉네임)를 기준으로 회원 목록을 조회합니다."
    )
    @GetMapping
    public CommonResponse<List<AdminMemberSummaryResponse>> getMembers(
            @RequestParam(required = false) String keyword
    ) {
        List<AdminMemberSummaryResponse> responses = adminSearchMembersUseCase.execute(keyword);

        return CommonResponse.success(
                ResponseMessage.ADMIN_MEMBER_LIST_GET_SUCCESS,
                responses
        );
    }

    @Operation(
            summary = "회원 상세 조회",
            description = "memberId를 기준으로 회원 상세 정보를 조회합니다."
    )
    @GetMapping("/{memberId}")
    public CommonResponse<AdminMemberDetailResponse> getMemberDetail(
            @PathVariable Long memberId
    ) {
        AdminMemberDetailResponse response = adminGetMemberDetailUseCase.execute(memberId);

        return CommonResponse.success(
                ResponseMessage.ADMIN_MEMBER_DETAIL_GET_SUCCESS,
                response
        );
    }

    @Operation(
            summary = "회원 정보 수정",
            description = "관리자가 회원의 이메일, 닉네임 정보를 수정합니다."
    )
    @PatchMapping("/{memberId}")
    public CommonResponse<Void> updateMember(
            @PathVariable Long memberId,
            @RequestBody AdminMemberUpdateRequest request
    ) {
        adminUpdateMemberUseCase.execute(memberId, request);

        return CommonResponse.success(
                ResponseMessage.ADMIN_MEMBER_UPDATE_SUCCESS
        );
    }
}
