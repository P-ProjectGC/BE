package plango.file.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import plango.file.application.dto.response.FileUploadResponse;
import plango.file.domain.service.FileUploadService;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class FileUploadUseCase {

    private final FileUploadService fileUploadService;

    public FileUploadResponse execute(MultipartFile file) throws IOException {
        String fileUrl = fileUploadService.upload(file);

        return FileUploadResponse.builder()
                .fileName(file.getOriginalFilename())
                .fileUrl(fileUrl)
                .build();
    }
}
