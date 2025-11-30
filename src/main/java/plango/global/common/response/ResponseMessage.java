package plango.global.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    // ===== 공통 =====
    SUCCESS(0, "성공"),

    // ===== 프로필 =====
    MEMBER_PROFILE_GET_SUCCESS(0, "프로필 조회 성공"),
    MEMBER_PROFILE_UPDATE_SUCCESS(0, "프로필 수정 성공"),

    // ===== 여행방 장소(RoomPlace) =====
    ROOM_PLACE_LIST_SUCCESS(0, "위시리스트 장소 조회 성공"),
    ROOM_PLACE_CREATE_SUCCESS(0, "위시리스트 장소 생성 성공"),
    ROOM_PLACE_DELETE_SUCCESS(0, "위시리스트 장소 삭제 성공"),

    // ===== 여행방 일정(RoomSchedule) =====
    ROOM_SCHEDULE_CREATE_SUCCESS(0, "일정 생성 성공"),
    ROOM_SCHEDULE_GET_SUCCESS(0, "일정 조회 성공"),
    ROOM_SCHEDULE_UPDATE_SUCCESS(0, "일정 수정 성공"),
    ROOM_SCHEDULE_DELETE_SUCCESS(0, "일정 삭제 성공");

    private final int code;
    private final String message;
}
