package plango.room.application.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import plango.member.domain.entity.Member;
import plango.room.application.dto.request.RoomCreateRequest;
import plango.room.application.dto.response.*;
import plango.room.domain.entity.Room;
import plango.room.domain.entity.RoomMember;

@Component
public class RoomMapper {

    public Room toEntity(RoomCreateRequest request, Member owner) {
        return new Room(
                request.roomName(),
                request.memo(),
                request.startDate(),
                request.endDate(),
                owner
        );
    }

    public RoomCreateResponse toCreateResponse(Room room) {
        List<Long> memberIds = room.getMembers()
                .stream()
                .map(RoomMember::getMember)
                .map(Member::getId)
                .collect(Collectors.toList());

        return RoomCreateResponse.builder()
                .roomId(room.getId())
                .roomName(room.getName())
                .memo(room.getMemo())
                .startDate(room.getStartDate())
                .endDate(room.getEndDate())
                .ownerId(room.getOwner().getId())
                .memberIds(memberIds)
                .build();
    }

    public RoomUpdateResponse toRoomUpdateResponse(Room room) {
        List<RoomUpdateResponse.MemberInfo> memberInfos = room.getMembers()
                .stream()
                .map(roomMember -> RoomUpdateResponse.MemberInfo.builder()
                        .memberId(roomMember.getMember().getId())
                        .nickname(roomMember.getMember().getNickname())
                        .isHost(room.getOwner().getId().equals(roomMember.getMember().getId()))
                        .build())
                .collect(Collectors.toList());

        return RoomUpdateResponse.builder()
                .roomId(room.getId())
                .roomName(room.getName())
                .hostId(room.getOwner().getId())
                .memo(room.getMemo())
                .members(memberInfos)
                .build();
    }

    /**
     * 방 요약 정보 + 내가 이 방에서 방장인지 여부(host).
     * host 계산은 UseCase 쪽에서 하고, 여기서는 그대로 매핑만 한다.
     */
    public RoomSummaryResponse toSummaryResponse(Room room, boolean host) {
        return RoomSummaryResponse.builder()
                .roomId(room.getId())
                .roomName(room.getName())
                .memo(room.getMemo())
                .startDate(room.getStartDate())
                .endDate(room.getEndDate())
                .host(host)
                .build();
    }

    /**
     * 개별 멤버 정보 + 이 멤버가 방장인지 여부(host).
     */
    public RoomMemberResponse toRoomMemberResponse(RoomMember roomMember) {
        boolean host = roomMember.getRole().name().equals("OWNER");

        return RoomMemberResponse.builder()
                .memberId(roomMember.getMember().getId())
                .nickname(roomMember.getMember().getNickname())
                .profileImageUrl(roomMember.getMember().getProfileImageUrl())
                .host(host)
                .build();
    }

    public RoomDetailResponse toRoomDetailResponse(
            Room room,
            boolean host,
            List<RoomMemberResponse> members
    ) {
        return RoomDetailResponse.builder()
                .roomId(room.getId())
                .roomName(room.getName())
                .memo(room.getMemo())
                .startDate(room.getStartDate())
                .endDate(room.getEndDate())
                .host(host)
                .members(members)
                .build();
    }
    public RoomListResponse toRoomListResponse(Room room) {
        return RoomListResponse.builder()
                .roomId(room.getId())
                .roomName(room.getName())
                .memo(room.getMemo())
                .startDate(room.getStartDate())
                .endDate(room.getEndDate())
                .host(true) // 너네가 원하면 수정
                .build();
    }
}
