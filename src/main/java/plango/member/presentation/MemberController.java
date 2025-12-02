package plango.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;
import plango.member.application.dto.request.MemberProfileUpdateRequest;
import plango.member.application.dto.request.ChangePasswordRequest;
import plango.member.application.dto.response.MemberProfileResponse;
import plango.member.application.usecase.GetMyProfileUseCase;
import plango.member.application.usecase.UpdateMyProfileUseCase;
import plango.member.application.usecase.ChangePasswordUseCase;
import plango.member.application.usecase.MemberWithdrawUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@Tag(name = "멤버 프로필", description = "회원 프로필 조회 및 수정 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final GetMyProfileUseCase getMyProfileUseCase;
    private final UpdateMyProfileUseCase updateMyProfileUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final MemberWithdrawUseCase memberWithdrawUseCase;

    @Operation(summary = "프로필 조회", description = "memberId를 기준으로 프로필 정보를 조회합니다.")
    @GetMapping("/{memberId}")
    public CommonResponse<MemberProfileResponse> getProfile(
            @PathVariable Long memberId
    ) {
        MemberProfileResponse response = getMyProfileUseCase.execute(memberId);
        return CommonResponse.success(
                ResponseMessage.MEMBER_PROFILE_GET_SUCCESS,
                response
        );
    }

    @Operation(summary = "프로필 수정", description = "nickname, profileImageUrl 값을 수정합니다.")
    @PatchMapping("/{memberId}")
    public CommonResponse<Void> updateProfile(
            @PathVariable Long memberId,
            @RequestBody @Valid MemberProfileUpdateRequest request
    ) {
        updateMyProfileUseCase.execute(memberId, request);
        return CommonResponse.success(
                ResponseMessage.MEMBER_PROFILE_UPDATE_SUCCESS
        );
    }

    @Operation(
            summary = "회원 탈퇴",
            description = "회원의 계정 및 연관 데이터를 모두 삭제합니다. (소셜 계정, 친구 관계, 여행방 등)"
    )
    @DeleteMapping("/{memberId}")
    public CommonResponse<Void> withdraw(
            @PathVariable Long memberId
    ) {
        memberWithdrawUseCase.execute(memberId);
        return CommonResponse.success(
                ResponseMessage.MEMBER_WITHDRAW_SUCCESS
        );
    }

    @Operation(summary = "비밀번호 변경", description = "현재 비밀번호를 검증하고 새 비밀번호로 변경합니다.")
    @PatchMapping("/{memberId}/password")
    public CommonResponse<Void> changePassword(
            @PathVariable Long memberId,
            @RequestBody @Valid ChangePasswordRequest request
    ) {
        changePasswordUseCase.execute(memberId, request);
        return CommonResponse.success(
                ResponseMessage.MEMBER_PASSWORD_CHANGE_SUCCESS
        );
    }
}
