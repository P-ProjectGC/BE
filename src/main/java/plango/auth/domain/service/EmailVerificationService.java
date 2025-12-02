package plango.auth.domain.service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import plango.global.common.exception.BusinessException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private static final long EXPIRATION_MILLIS = 5 * 60 * 1000L;

    private final Map<String, VerificationInfo> store = new ConcurrentHashMap<>();

    public String createAndSendVerificationCode(String email) {
        String code = generateCode();

        VerificationInfo info = new VerificationInfo(code, Instant.now());
        store.put(email, info);

        log.info("[EmailVerification] email = {}, code = {}", email, code);

        return code;
    }

    public void verifyCode(String email, String code) {
        VerificationInfo info = store.get(email);

        if (info == null) {
            throw new BusinessException(
                    HttpStatus.BAD_REQUEST.value(),
                    "인증번호가 존재하지 않습니다."
            );
        }

        if (!info.code.equals(code)) {
            throw new BusinessException(
                    HttpStatus.BAD_REQUEST.value(),
                    "인증번호가 일치하지 않습니다."
            );
        }

        Instant now = Instant.now();

        if (now.isAfter(info.createdAt.plusMillis(EXPIRATION_MILLIS))) {
            store.remove(email);

            throw new BusinessException(
                    HttpStatus.BAD_REQUEST.value(),
                    "인증번호 유효 시간이 만료되었습니다."
            );
        }

        store.remove(email);
    }

    private String generateCode() {
        int value = ThreadLocalRandom.current().nextInt(0, 1_000_000);

        return String.format("%06d", value);
    }

    public String maskEmail(String email) {
        if (email == null || email.isBlank()) {
            return "";
        }

        int atIndex = email.indexOf('@');

        if (atIndex <= 0) {
            return email;
        }

        String local = email.substring(0, atIndex);
        String domain = email.substring(atIndex);

        if (local.length() <= 2) {
            return "**" + domain;
        }

        String visible = local.substring(0, 2);
        String masked = "*".repeat(local.length() - 2);

        return visible + masked + domain;
    }

    @Getter
    @RequiredArgsConstructor
    private static class VerificationInfo {

        private final String code;
        private final Instant createdAt;
    }
}
