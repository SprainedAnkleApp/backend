package pl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.edu.agh.ki.io.cloudstorage.GoogleCloudFileService;

@Configuration
public class AppConfig {
    @Bean
    public GoogleCloudFileService googleCloudFileService() {
        return new GoogleCloudFileService();
    }
}
