package plango.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class AmazonS3Config {

    private final Environment environment;

    @Bean
    public S3Client s3Client() {
        String region = environment.getProperty("S3_REGION", "ap-southeast-2");

        String accessKey = environment.getProperty("S3_ACCESS_KEY_ID");
        String secretKey = environment.getProperty("S3_SECRET_ACCESS_KEY");

        if (accessKey != null && !accessKey.isBlank()
                && secretKey != null && !secretKey.isBlank()) {

            AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

            return S3Client.builder()
                    .region(Region.of(region))
                    .credentialsProvider(StaticCredentialsProvider.create(credentials))
                    .build();
        }

        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }
}