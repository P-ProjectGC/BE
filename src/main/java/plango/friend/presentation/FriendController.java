package plango.friend.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plango.friend.application.dto.request.FriendRequestRequest;
import plango.friend.application.dto.response.FriendResponse;
import plango.friend.application.usecase.FriendRequestUseCase;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;

@Tag(name = "Friend API", description = "친구 추가 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friends")
public class FriendController {

    private final FriendRequestUseCase friendRequestUseCase;

    @Operation(summary = "닉네임으로 친구 요청", description = "앱 내 닉네임으로 친구 요청을 생성합니다.")
    @PostMapping
    public CommonResponse<FriendResponse> requestFriend(
            @RequestHeader("X-Member-Id") Long memberId,
            @RequestBody @Valid FriendRequestRequest request
    ) {
        FriendResponse response = friendRequestUseCase.execute(memberId, request);
        return CommonResponse.success(ResponseMessage.FRIEND_REQUEST_CREATE_SUCCESS, response);
    }
}
