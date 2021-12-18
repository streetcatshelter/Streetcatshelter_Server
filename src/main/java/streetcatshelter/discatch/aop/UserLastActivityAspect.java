package streetcatshelter.discatch.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class UserLastActivityAspect {
    private final UserRepository userRepository;

    @After("@annotation(streetcatshelter.discatch.aop.UpdateUserLastActivity)")
    public void UpdateUserLastActivity(JoinPoint joinPoint) throws Exception {
        User user = Arrays.stream(joinPoint.getArgs())
                .filter(User.class::isInstance)
                .map(User.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User를 찾을 수 없습니다."));

        user.setLastActivity(LocalDateTime.now());
        userRepository.save(user);
    }
}
