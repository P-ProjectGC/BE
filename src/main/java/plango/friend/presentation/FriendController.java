package plango.friend.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plango.friend.application.dto.request.FriendRequestRequest;
import plango.friend.application.dto.request.KakaoFriendSyncRequest;
import plango.friend.application.dto.response.FriendResponse;
import plango.friend.application.usecase.FriendAcceptUseCase;
import plango.friend.application.usecase.FriendCancelUseCase;
import plango.friend.application.usecase.FriendRejectUseCase;
import plango.friend.application.usecase.FriendRequestUseCase;
import plango.friend.application.usecase.KakaoFriendSyncUseCase;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;

@Tag(name = "Friend", description = "친구 요청 및 관리 API")
@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendRequestUseCase friendRequestUseCase;
    private final FriendAcceptUseCase friendAcceptUseCase;
    private final FriendRejectUseCase friendRejectUseCase;
    private final FriendCancelUseCase friendCancelUseCase;
    private final KakaoFriendSyncUseCase kakaoFriendSyncUseCase;

    @Operation(summary = "닉네임으로 친구 요청", description = "앱 내 닉네임으로 친구 요청을 생성합니다.")
    @PostMapping
    public CommonResponse<FriendResponse> requestFriend(
            @RequestHeader("X-Member-Id") Long memberId,
            @RequestBody @Valid FriendRequestRequest request
    ) {
        FriendResponse response = friendRequestUseCase.execute(memberId, request);
        return CommonResponse.success(
                ResponseMessage.FRIEND_REQUEST_CREATE_SUCCESS,
                response
        );
    }

    @Operation(summary = "친구 요청 수락", description = "받은 친구 요청을 수락합니다.")
    @PostMapping("/{friendId}/accept")
    public CommonResponse<FriendResponse> acceptFriend(
            @RequestHeader("X-Member-Id") Long memberId,
            @PathVariable Long friendId
    ) {
        FriendResponse response = friendAcceptUseCase.execute(memberId, friendId);
        return CommonResponse.success(
                ResponseMessage.FRIEND_ACCEPT_SUCCESS,
                response
        );
    }

    @Operation(summary = "친구 요청 거절", description = "받은 친구 요청을 거절합니다.")
    @PostMapping("/{friendId}/reject")
    public CommonResponse<Void> rejectFriend(
            @RequestHeader("X-Member-Id") Long memberId,
            @PathVariable Long friendId
    ) {
        friendRejectUseCase.execute(memberId, friendId);
        return CommonResponse.success(
                ResponseMessage.FRIEND_REJECT_SUCCESS
        );
    }

    @Operation(summary = "친구 요청 취소", description = "보낸 친구 요청을 취소합니다.")
    @PostMapping("/{friendId}/cancel")
    public CommonResponse<Void> cancelFriend(
            @RequestHeader("X-Member-Id") Long memberId,
            @PathVariable Long friendId
    ) {
        friendCancelUseCase.execute(memberId, friendId);
        return CommonResponse.success(
                ResponseMessage.FRIEND_CANCEL_SUCCESS
        );
    }

    @Operation(
            summary = "카카오 친구 연동",
            description = "카카오 인가 코드를 사용해 카카오 친구 중 PlanGo에 가입된 사용자들과 자동으로 친구를 맺습니다."
    )
    @PostMapping("/kakao/sync")
    public CommonResponse<List<FriendResponse>> syncKakaoFriends(
            @RequestHeader("X-Member-Id") Long memberId,
            @RequestBody @Valid KakaoFriendSyncRequest request
    ) {
        List<FriendResponse> responses = kakaoFriendSyncUseCase.execute(memberId, request);
        return CommonResponse.success(
                ResponseMessage.KAKAO_FRIEND_SYNC_SUCCESS,
                responses
        );
    }
}
