package plango.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AmazonS3Config {

    @Bean
    public S3Client s3Client() {
        // 1) .env에 있는 S3_REGION 우선 사용
        String region = System.getenv("S3_REGION");

        // 2) 없으면 기본값(네 dev 버킷 리전)으로 세팅
        if (region == null || region.isBlank()) {
            region = "ap-southeast-2"; // dev 기본 리전
        }

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }
}
