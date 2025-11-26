package plango.file.application.dto.response;

import lombok.Builder;

@Builder
public record FileUploadResponse(
        String fileName,
        String fileUrl
) {
}
