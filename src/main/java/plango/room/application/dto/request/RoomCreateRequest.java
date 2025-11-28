package plango.room.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record RoomCreateRequest(

        @NotBlank(message = "여행방 이름은 필수입니다.")
        @Size(max = 50, message = "여행방 이름은 50자를 초과할 수 없습니다.")
        String roomName,

        String memo,

        @NotNull(message = "여행 시작일은 필수입니다.")
        LocalDate startDate,

        @NotNull(message = "여행 종료일은 필수입니다.")
        LocalDate endDate,

        @NotEmpty(message = "여행 멤버는 최소 1명 이상이어야 합니다.")
        List<@NotNull Long> memberIds
) {}
