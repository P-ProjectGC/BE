package plango.member.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;
import plango.member.application.dto.request.MemberProfileUpdateRequest;
import plango.member.application.dto.response.MemberProfileResponse;
import plango.member.application.usecase.GetMyProfileUseCase;
import plango.member.application.usecase.UpdateMyProfileUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final GetMyProfileUseCase getMyProfileUseCase;
    private final UpdateMyProfileUseCase updateMyProfileUseCase;

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

    @PatchMapping("/{memberId}")
    public CommonResponse<Void> updateProfile(
            @PathVariable Long memberId,
            @RequestBody MemberProfileUpdateRequest request
    ) {
        updateMyProfileUseCase.execute(memberId, request);
        return CommonResponse.success(
                ResponseMessage.MEMBER_PROFILE_UPDATE_SUCCESS
        );
    }
}
