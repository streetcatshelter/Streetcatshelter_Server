package streetcatshelter.discatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import streetcatshelter.discatch.config.properties.AppProperties;
import streetcatshelter.discatch.config.properties.CorsProperties;


@SpringBootApplication
@EnableConfigurationProperties({
        CorsProperties.class,
        AppProperties.class
})
@EnableJpaAuditing
public class DisCatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisCatchApplication.class, args);
    }

}
