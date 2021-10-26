package streetcatshelter.discatch.domain.config.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import streetcatshelter.discatch.domain.oauth.filter.CORSFilter;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean getFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean<>(new CORSFilter());
        registrationBean.addUrlPatterns("/**");
        return registrationBean;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //cors를 적용할 URL 패턴 정의
                .allowedOriginPatterns("*")// 자원 공유 허락할 Origin
                .allowedOrigins("*")
                .allowedMethods("*") //허락할 HTTP method 지정
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}
