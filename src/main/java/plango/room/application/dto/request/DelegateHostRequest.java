package plango.room.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record DelegateHostRequest(
        @NotNull
        Long newHostId
) {}
