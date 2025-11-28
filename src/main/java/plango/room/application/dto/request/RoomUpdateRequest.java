package plango.room.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RoomUpdateRequest(

        @NotEmpty(message = "여행방 멤버 목록은 비어 있을 수 없습니다.")
        List<Long> memberIds,

        @NotNull(message = "방장 ID는 필수입니다.")
        Long hostId,

        @NotBlank(message = "여행방 이름은 비어 있을 수 없습니다.")
        String roomName,

        String memo
) {
}
