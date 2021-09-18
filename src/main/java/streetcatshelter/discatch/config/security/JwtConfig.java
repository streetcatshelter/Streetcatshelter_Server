package streetcatshelter.discatch.config.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import streetcatshelter.discatch.oauth.service.CustomUserDetailsService;
import streetcatshelter.discatch.oauth.token.AuthTokenProvider;

@Configuration
@PropertySource("/application.yml")
@RequiredArgsConstructor
public class JwtConfig {


    @Value("${jwt.secret}")
    private String secret;

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public AuthTokenProvider jwtProvider(){
        return new AuthTokenProvider(secret, customUserDetailsService);
    }

}
