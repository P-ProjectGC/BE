package plango.room.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;
import plango.room.application.dto.response.RoomDetailResponse;
import plango.room.application.dto.response.RoomListResponse;
import plango.room.application.usecase.GetRoomDetailUseCase;
import plango.room.application.usecase.GetRoomListUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@Tag(name = "Room 조회 API")
public class RoomGetController {

    private final GetRoomListUseCase getRoomListUseCase;
    private final GetRoomDetailUseCase getRoomDetailUseCase;

    @GetMapping
    @Operation(summary = "여행방 목록 조회")
    public CommonResponse<List<RoomListResponse>> getRoomList(
            @AuthenticationPrincipal Long memberId,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        List<RoomListResponse> result =
                (keyword == null || keyword.isBlank())
                        ? getRoomListUseCase.getMyRoomList(memberId)
                        : getRoomListUseCase.searchMyRooms(memberId, keyword);

        return CommonResponse.success(ResponseMessage.ROOM_LIST_GET_SUCCESS, result);
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "여행방 상세 조회")
    public CommonResponse<RoomDetailResponse> getRoomDetail(
            @PathVariable Long roomId,
            @AuthenticationPrincipal Long memberId
    ) {
        RoomDetailResponse response = getRoomDetailUseCase.execute(roomId, memberId);
        return CommonResponse.success(ResponseMessage.ROOM_DETAIL_GET_SUCCESS, response);
    }
}
