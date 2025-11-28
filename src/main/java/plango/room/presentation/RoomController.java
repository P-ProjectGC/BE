package plango.room.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;
import plango.room.application.dto.request.RoomCreateRequest;
import plango.room.application.dto.request.RoomUpdateRequest;
import plango.room.application.dto.response.RoomCreateResponse;
import plango.room.application.dto.response.RoomUpdateResponse;
import plango.room.application.usecase.CreateRoomUseCase;
import plango.room.application.usecase.UpdateRoomUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
@Tag(name = "room-controller", description = "여행방 API")
public class RoomController {

    private final CreateRoomUseCase createRoomUseCase;
    private final UpdateRoomUseCase updateRoomUseCase;

    @PostMapping
    @Operation(summary = "여행방 생성", description = "로그인한 사용자를 방장으로 하여 여행방을 생성합니다.")
    public CommonResponse<RoomCreateResponse> createRoom(
            @RequestHeader("X-MEMBER-ID") Long memberId,
            @Valid @RequestBody RoomCreateRequest request
    ) {
        RoomCreateResponse response = createRoomUseCase.execute(memberId, request);
        return CommonResponse.success(ResponseMessage.SUCCESS, response);
    }

    @PatchMapping("/{roomId}")
    @Operation(summary = "여행방 수정", description = "여행방 멤버, 방장, 방 정보를 수정합니다.")
    public CommonResponse<RoomUpdateResponse> updateRoom(
            @PathVariable Long roomId,
            @Valid @RequestBody RoomUpdateRequest request
    ) {
        RoomUpdateResponse response = updateRoomUseCase.updateRoom(roomId, request);
        return CommonResponse.success(ResponseMessage.SUCCESS, response);
    }
}
