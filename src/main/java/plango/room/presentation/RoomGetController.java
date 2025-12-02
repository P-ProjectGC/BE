package plango.room.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;
import plango.room.application.dto.response.RoomDetailResponse;
import plango.room.application.dto.response.RoomListResponse;
import plango.room.application.usecase.GetRoomListUseCase;
import plango.room.application.usecase.GetRoomDetailUseCase;

import java.util.List;

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
            @RequestHeader("X-MEMBER-ID") Long memberId,
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
            @RequestHeader("X-MEMBER-ID") Long memberId,
            @PathVariable Long roomId
    ) {
        RoomDetailResponse response = getRoomDetailUseCase.execute(memberId, roomId);
        return CommonResponse.success(ResponseMessage.ROOM_DETAIL_GET_SUCCESS, response);
    }
}
