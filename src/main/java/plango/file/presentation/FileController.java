package plango.file.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plango.file.application.dto.response.FileUploadResponse;
import plango.file.application.usecase.FileUploadUseCase;
import plango.global.common.response.CommonResponse;

import java.io.IOException;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestPart;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileUploadUseCase fileUploadUseCase;

    @Operation(summary = "파일 업로드", description = "S3에 파일을 업로드합니다.")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse<FileUploadResponse> upload(
            @RequestPart("file") @Parameter(description = "업로드할 파일") MultipartFile file
    ) {
        try {
            FileUploadResponse response = fileUploadUseCase.execute(file);
            return CommonResponse.createSuccess("파일 업로드 성공", response);
        } catch (IOException e) {
            return CommonResponse.createFailure(500, "파일 업로드 중 오류가 발생했습니다.");
        }
    }
}