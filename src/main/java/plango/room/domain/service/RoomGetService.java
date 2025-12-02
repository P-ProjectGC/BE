package plango.room.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plango.global.common.exception.BusinessException;
import plango.room.domain.entity.Room;
import plango.room.domain.entity.RoomMember;
import plango.room.domain.repository.RoomRepository;
import plango.room.domain.repository.RoomMemberRepository;
import plango.room.exception.RoomErrorCode;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomGetService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;

    /**
     * 특정 멤버가 속한 모든 여행방 조회
     */
    public List<Room> getRoomsByMemberId(Long memberId) {
        return roomRepository.findByMemberId(memberId);
    }

    /**
     * 특정 멤버의 여행방 중 이름/메모에 keyword 가 포함된 방만 조회
     */
    public List<Room> getRoomsByMemberAndKeyword(Long memberId, String keyword) {
        return roomRepository.findByMemberIdAndKeyword(memberId, keyword);
    }

    /**
     * 시작일 기준으로 여행방 조회 (필요 시 사용)
     */
    public List<Room> getRoomsByStartDate(LocalDate startDate) {
        return roomRepository.findByStartDate(startDate);
    }

    public List<RoomMember> getRoomMembersByRoomId(Long roomId) {
        return roomMemberRepository.findByRoomId(roomId);
    }

    /**
     * 여행방 상세 조회를 위한 단건 조회
     */
    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException(
                        RoomErrorCode.ROOM_NOT_FOUND.getCode(),
                        RoomErrorCode.ROOM_NOT_FOUND.getMessage()
                ));
    }
}
