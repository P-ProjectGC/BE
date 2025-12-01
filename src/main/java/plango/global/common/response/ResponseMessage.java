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
    MEMBER_PASSWORD_CHANGE_SUCCESS(0,"비밀번호 변경에 성공했습니다."),

    // ===== 여행방 장소 (RoomPlace) =====
    ROOM_PLACE_LIST_SUCCESS(0, "위시리스트 장소 조회 성공"),
    ROOM_PLACE_CREATE_SUCCESS(0, "위시리스트 장소 생성 성공"),
    ROOM_PLACE_DELETE_SUCCESS(0, "위시리스트 장소 삭제 성공"),

    // ===== 여행방 일정 (RoomSchedule) =====
    ROOM_SCHEDULE_CREATE_SUCCESS(0, "일정 생성 성공"),
    ROOM_SCHEDULE_GET_SUCCESS(0, "일정 조회 성공"),
    ROOM_SCHEDULE_UPDATE_SUCCESS(0, "일정 수정 성공"),
    ROOM_SCHEDULE_DELETE_SUCCESS(0, "일정 삭제 성공"),

    // ===== 닉네임 =====
    NICKNAME_UPDATE_SUCCESS(0, "닉네임 수정 성공"),
    NICKNAME_CREATE_SUCCESS(0, "닉네임 생성 성공"),

    // ===== 친구 요청 / 관리 =====
    FRIEND_REQUEST_CREATE_SUCCESS(0, "친구 요청 생성 성공"),
    FRIEND_REQUEST_ALREADY_EXISTS(0, "이미 친구 요청이 존재합니다."),
    FRIEND_REQUEST_INVALID_SELF(0, "자기 자신에게는 친구 요청을 보낼 수 없습니다."),
    FRIEND_TARGET_NOT_FOUND(0, "해당 닉네임의 사용자를 찾을 수 없습니다."),
    FRIEND_ACCEPT_SUCCESS(0, "친구 요청 수락 성공"),
    FRIEND_REJECT_SUCCESS(0, "친구 요청 거절 성공"),
    FRIEND_CANCEL_SUCCESS(0, "친구 요청 취소 성공");

    private final int code;
    private final String message;
}
