package plango.room.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record RoomCreateRequest(

        @NotBlank
        @Size(max = 50)
        String roomName,

        String memo,

        @NotNull
        LocalDate startDate,

        @NotNull
        LocalDate endDate,

        @NotEmpty
        List<Long> memberIds
) {}
