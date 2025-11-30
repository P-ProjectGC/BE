package plango.room.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;
import plango.room.application.dto.request.RoomPlaceCreateRequest;
import plango.room.application.dto.response.RoomPlaceResponse;
import plango.room.application.usecase.RoomPlaceCommandUseCase;
import plango.room.application.usecase.RoomPlaceQueryUseCase;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms/{roomId}/places")
@Tag(name = "room-place-controller", description = "여행방 장소(위시리스트) API")
public class RoomPlaceController {

    private final RoomPlaceCommandUseCase roomPlaceCommandUseCase;
    private final RoomPlaceQueryUseCase roomPlaceQueryUseCase;

    @GetMapping
    @Operation(summary = "위시리스트 장소 조회", description = "해당 여행방의 모든 위시리스트 장소를 조회합니다.")
    public CommonResponse<List<RoomPlaceResponse>> getPlaces(
            @PathVariable Long roomId
    ) {
        List<RoomPlaceResponse> responses = roomPlaceQueryUseCase.getPlaces(roomId);
        return CommonResponse.success(ResponseMessage.SUCCESS, responses);
    }

    @PostMapping
    @Operation(summary = "위시리스트 장소 생성", description = "여행방에 장소를 추가합니다.")
    public CommonResponse<RoomPlaceResponse> createPlace(
            @PathVariable Long roomId,
            @RequestHeader("X-MEMBER-ID") Long memberId,
            @Valid @RequestBody RoomPlaceCreateRequest request
    ) {
        RoomPlaceResponse response = roomPlaceCommandUseCase.createRoomPlace(
                roomId,
                memberId,
                request
        );
        return CommonResponse.success(ResponseMessage.SUCCESS, response);
    }

    @DeleteMapping("/{placeId}")
    @Operation(summary = "위시리스트 장소 삭제", description = "여행방의 특정 장소를 삭제합니다.")
    public CommonResponse<Void> deletePlace(
            @PathVariable Long placeId
    ) {
        roomPlaceCommandUseCase.deleteRoomPlace(placeId);
        return CommonResponse.success(ResponseMessage.SUCCESS);
    }
}

