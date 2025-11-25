package plango.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plango.application.usecase.FileUploadUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileUploadUseCase fileUploadUseCase;

    @Operation(summary = "파일 업로드", description = "S3에 파일을 업로드합니다.")
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE   // ★ 이 부분 추가
    )
    public String upload(@RequestPart("file") MultipartFile file) throws Exception {
        return fileUploadUseCase.execute(file);
    }
}
