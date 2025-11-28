package plango.room.application.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record RoomUpdateResponse(

        Long roomId,

        String roomName,

        Long hostId,

        String memo,

        List<MemberInfo> members
) {

    @Builder
    public record MemberInfo(

            Long memberId,

            String nickname,

            boolean isHost
    ) {
    }
}
