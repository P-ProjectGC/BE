package plango.room.application.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import plango.member.domain.entity.Member;
import plango.room.application.dto.request.RoomCreateRequest;
import plango.room.application.dto.response.RoomCreateResponse;
import plango.room.application.dto.response.RoomUpdateResponse;
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
}
