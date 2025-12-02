package plango.room.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plango.room.application.dto.response.RoomListResponse;
import plango.room.domain.entity.Room;
import plango.room.domain.service.RoomGetService;
import plango.room.application.dto.response.RoomSummaryResponse;
import plango.room.application.mapper.RoomMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRoomListUseCase {

    private final RoomGetService roomGetService;
    private final RoomMapper roomMapper;

    public List<RoomListResponse> execute(Long memberId, String keyword) {

        // 1) 키워드 없으면 전체 조회
        if (keyword == null || keyword.isBlank()) {
            return getMyRoomList(memberId);
        }

        // 2) 키워드 있으면 검색
        return searchMyRooms(memberId, keyword);
    }

    public List<RoomListResponse> getMyRoomList(Long memberId) {
        return roomGetService.getRoomsByMemberId(memberId)
                .stream()
                .map(roomMapper::toRoomListResponse)
                .toList();
    }

    public List<RoomListResponse> searchMyRooms(Long memberId, String keyword) {
        return roomGetService.getRoomsByMemberAndKeyword(memberId, keyword)
                .stream()
                .map(roomMapper::toRoomListResponse)
                .toList();
    }
}