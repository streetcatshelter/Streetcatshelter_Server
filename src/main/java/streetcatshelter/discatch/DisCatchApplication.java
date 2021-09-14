package streetcatshelter.discatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DisCatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisCatchApplication.class, args);
    }

}
