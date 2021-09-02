package au.com.alintaenergy.customercrud;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerCRUDApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerCRUDApplication.class, args);
    }

    @Bean
    public OpenAPI configureOpenAPI(@Value("${openapi.title:}") String appTitle,
            @Value("${openapi.version:}") String appVersion, @Value("${openapi.description:}") String appDesciption) {
        return new OpenAPI()
                .info(new Info()
                        .title(appTitle)
                        .version(appVersion)
                        .description(appDesciption));
    }
}
