package plango.friend.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plango.friend.application.dto.request.FriendRequestRequest;
import plango.friend.application.dto.response.FriendListItemResponse;
import plango.friend.application.dto.response.FriendRequestListItemResponse;
import plango.friend.application.dto.response.FriendResponse;
import plango.friend.application.usecase.FriendAcceptUseCase;
import plango.friend.application.usecase.FriendCancelUseCase;
import plango.friend.application.usecase.FriendListQueryUseCase;
import plango.friend.application.usecase.FriendReceivedRequestListQueryUseCase;
import plango.friend.application.usecase.FriendRejectUseCase;
import plango.friend.application.usecase.FriendRequestUseCase;
import plango.friend.application.usecase.FriendSentRequestListQueryUseCase;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;

@Tag(
        name = "Friend API",
        description = "친구 요청 및 관리 API"
)
@RestController
@RequestMapping("/api/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendRequestUseCase friendRequestUseCase;

    private final FriendAcceptUseCase friendAcceptUseCase;

    private final FriendRejectUseCase friendRejectUseCase;

    private final FriendCancelUseCase friendCancelUseCase;

    private final FriendListQueryUseCase friendListQueryUseCase;

    private final FriendReceivedRequestListQueryUseCase friendReceivedRequestListQueryUseCase;

    private final FriendSentRequestListQueryUseCase friendSentRequestListQueryUseCase;

    @Operation(
            summary = "친구 목록 조회",
            description = "내 친구 목록을 조회합니다. 닉네임 검색어를 전달하면 해당 닉네임을 포함하는 친구만 조회합니다."
    )
    @GetMapping
    public CommonResponse<List<FriendListItemResponse>> getFriends(
            @RequestHeader("X-Member-Id") Long memberId,
            @RequestParam(required = false) String nickname
    ) {
        List<FriendListItemResponse> responses = friendListQueryUseCase.execute(memberId, nickname);

        return CommonResponse.success(
                ResponseMessage.FRIEND_LIST_GET_SUCCESS,
                responses
        );
    }

    @Operation(
            summary = "받은 친구 요청 목록 조회",
            description = "현재 사용자에게 도착한 친구 요청 목록을 조회합니다."
    )
    @GetMapping("/requests/received")
    public CommonResponse<List<FriendRequestListItemResponse>> getReceivedFriendRequests(
            @RequestHeader("X-Member-Id") Long memberId
    ) {
        List<FriendRequestListItemResponse> responses =
                friendReceivedRequestListQueryUseCase.execute(memberId);

        return CommonResponse.success(
                ResponseMessage.FRIEND_REQUEST_RECEIVED_LIST_GET_SUCCESS,
                responses
        );
    }

    @Operation(
            summary = "보낸 친구 요청 목록 조회",
            description = "현재 사용자가 보낸 친구 요청 목록을 조회합니다."
    )
    @GetMapping("/requests/sent")
    public CommonResponse<List<FriendRequestListItemResponse>> getSentFriendRequests(
            @RequestHeader("X-Member-Id") Long memberId
    ) {
        List<FriendRequestListItemResponse> responses =
                friendSentRequestListQueryUseCase.execute(memberId);

        return CommonResponse.success(
                ResponseMessage.FRIEND_REQUEST_SENT_LIST_GET_SUCCESS,
                responses
        );
    }

    @Operation(
            summary = "친구 요청",
            description = "닉네임으로 친구 요청을 보냅니다."
    )
    @PostMapping("/request")
    public CommonResponse<FriendResponse> requestFriend(
            @RequestHeader("X-Member-Id") Long memberId,
            @Valid @RequestBody FriendRequestRequest request
    ) {
        FriendResponse response = friendRequestUseCase.execute(memberId, request);

        return CommonResponse.success(
                ResponseMessage.FRIEND_REQUEST_CREATE_SUCCESS,
                response
        );
    }

    @Operation(
            summary = "친구 요청 수락",
            description = "받은 친구 요청을 수락합니다."
    )
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

    @Operation(
            summary = "친구 요청 거절",
            description = "받은 친구 요청을 거절합니다."
    )
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

    @Operation(
            summary = "친구 요청 취소",
            description = "보낸 친구 요청을 취소합니다."
    )
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
}
