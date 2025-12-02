package plango.room.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomErrorCode {

    ROOM_NOT_FOUND(404, "여행방을 찾을 수 없습니다."),
    ROOM_HOST_NOT_IN_MEMBERS(400, "방장은 여행방 멤버 목록 안에 포함되어야 합니다."),
    MEMBER_NOT_FOUND(404, "멤버를 찾을 수 없습니다.");

    private final int code;

    private final String message;
}