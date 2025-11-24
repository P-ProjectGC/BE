package plango.application.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import plango.domain.service.FileUploadService;

@Component
@RequiredArgsConstructor
public class FileUploadUseCase {

    private final FileUploadService fileUploadService;

    public String execute(MultipartFile file) throws Exception {
        return fileUploadService.upload(file);
    }
}
