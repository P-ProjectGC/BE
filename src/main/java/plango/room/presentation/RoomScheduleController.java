package plango.room.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import plango.global.common.response.CommonResponse;
import plango.global.common.response.ResponseMessage;
import plango.room.application.dto.request.ScheduleCreateRequest;
import plango.room.application.dto.response.ScheduleCreateResponse;
import plango.room.application.usecase.RoomScheduleCommandUseCase;
import plango.room.application.usecase.RoomScheduleQueryUseCase;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms/{roomId}/schedules")
@Tag(name = "room-schedule-controller", description = "여행방 일정 API")
public class RoomScheduleController {

    private final RoomScheduleCommandUseCase roomScheduleCommandUseCase;
    private final RoomScheduleQueryUseCase roomScheduleQueryUseCase;

    @PostMapping
    @Operation(summary = "일정 생성", description = "특정 날짜(dayIndex)에 일정을 생성합니다.")
    public CommonResponse<ScheduleCreateResponse> createSchedule(
            @PathVariable Long roomId,
            @RequestHeader("X-MEMBER-ID") Long memberId,
            @Valid @RequestBody ScheduleCreateRequest request
    ) {
        ScheduleCreateResponse response = roomScheduleCommandUseCase.createSchedule(memberId, roomId, request);
        return CommonResponse.success(ResponseMessage.ROOM_SCHEDULE_CREATE_SUCCESS, response);
    }

    @GetMapping
    @Operation(summary = "일정 조회", description = "특정 날짜(dayIndex)의 일정을 조회합니다.")
    public CommonResponse<List<ScheduleCreateResponse>> getSchedules(
            @PathVariable Long roomId,
            @RequestParam int dayIndex
    ) {
        List<ScheduleCreateResponse> responses = roomScheduleQueryUseCase.getSchedules(roomId, dayIndex);
        return CommonResponse.success(ResponseMessage.ROOM_SCHEDULE_GET_SUCCESS, responses);
    }

    @PatchMapping("/{scheduleId}")
    @Operation(summary = "일정 수정", description = "시작/종료 시간 또는 메모를 수정합니다.")
    public CommonResponse<Void> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestHeader("X-MEMBER-ID") Long memberId,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) String memo
    ) {
        roomScheduleCommandUseCase.updateSchedule(memberId, scheduleId, startTime, endTime, memo);
        return CommonResponse.success(ResponseMessage.ROOM_SCHEDULE_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{scheduleId}")
    @Operation(summary = "일정 삭제", description = "일정을 삭제합니다.")
    public CommonResponse<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @RequestHeader("X-MEMBER-ID") Long memberId
    ) {
        roomScheduleCommandUseCase.deleteSchedule(memberId, scheduleId);
        return CommonResponse.success(ResponseMessage.ROOM_SCHEDULE_DELETE_SUCCESS);
    }
}
