package plango.global.common.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Status", description = "상태 체크 API")
@RestController
public class StatusCheckController {

    @Operation(summary = "헬스 체크", description = "애플리케이션 상태를 확인합니다.")
    @GetMapping("/health-check")
    public ResponseEntity<String> checkHealthStatus() {
        return ResponseEntity.ok("OK");
    }
}
