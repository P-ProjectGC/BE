package plango.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI plangoOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("PlanGo API")
                        .description("PlanGo 여행 일정/방/회원 API 문서")
                        .version("v1.0.0"));
    }
}
