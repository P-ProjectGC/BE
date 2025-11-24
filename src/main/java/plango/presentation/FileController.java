package plango.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plango.application.usecase.FileUploadUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileUploadUseCase fileUploadUseCase;

    @Operation(summary = "파일 업로드", description = "S3에 파일을 업로드합니다.")
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception {
        return fileUploadUseCase.execute(file);
    }
}
