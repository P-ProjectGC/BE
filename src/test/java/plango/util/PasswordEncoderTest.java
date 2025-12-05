package plango.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String rawPassword = "admin1234!";  // 네가 쓸 관리자 비번
        String encoded = passwordEncoder.encode(rawPassword);

        System.out.println(encoded);
    }
}
