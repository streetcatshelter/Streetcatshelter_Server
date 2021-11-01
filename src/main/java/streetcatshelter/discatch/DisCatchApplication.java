package streetcatshelter.discatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import streetcatshelter.discatch.domain.config.properties.AppProperties;
import streetcatshelter.discatch.domain.config.properties.CorsProperties;


@SpringBootApplication
@EnableConfigurationProperties({
        CorsProperties.class,
        AppProperties.class
})
@EnableJpaAuditing
@EnableScheduling
@EnableAspectJAutoProxy
public class DisCatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisCatchApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080", "http://localhost:3000") // 스프링, 리액트 포트
                        .allowedOrigins("*")
                        .maxAge(3000)
                        .allowedMethods(
                                HttpMethod.GET.name(),
                                HttpMethod.HEAD.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.DELETE.name());
            }
        };
    }

}
