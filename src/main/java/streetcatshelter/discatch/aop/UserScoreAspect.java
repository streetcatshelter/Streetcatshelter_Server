package streetcatshelter.discatch.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import streetcatshelter.discatch.domain.cat.repository.CatDetailRepository;
import streetcatshelter.discatch.domain.cat.repository.CatRepository;
import streetcatshelter.discatch.domain.community.repository.CommunityRepository;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.domain.user.domain.UserLevel;
import streetcatshelter.discatch.domain.user.repository.UserRepository;
import streetcatshelter.discatch.repository.CommentRepository;

import java.util.Arrays;

import static streetcatshelter.discatch.domain.user.domain.UserLevel.*;

@Aspect
@Component
@RequiredArgsConstructor
public class UserScoreAspect {
    private final CatRepository catRepository;
    private final CatDetailRepository catDetailRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    @After("@annotation(streetcatshelter.discatch.aop.UpdateUserScore)")
    public int UpdateUserScore(JoinPoint joinPoint) throws Exception {
        User user = Arrays.stream(joinPoint.getArgs())
                .filter(User.class::isInstance)
                .map(User.class::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("User를 찾을 수 없습니다."));

        UserLevel currentLevel = user.getUserLevel();
        if(currentLevel == null) {
            user.setUserLevel(아깽이);
        }
        int MIN_POINT_FOR_SILVER = 20;
        int MIN_POINT_FOR_GOLD = 50;
        int MIN_POINT_FOR_PLATINUM = 100;
        int userLevelScore;
        int A = catRepository.countAllByUser_UserSeq(user.getUserSeq());
        int B = catDetailRepository.countAllByUser_UserSeq(user.getUserSeq());
        int C = commentRepository.countAllByUser_UserSeq(user.getUserSeq());
        int D = communityRepository.countAllByUser_UserSeq(user.getUserSeq());
        userLevelScore = (A*5 + B*2 + C*2 + D*5);
        user.setScore(userLevelScore);

        if(currentLevel == 아깽이 && userLevelScore >= MIN_POINT_FOR_SILVER) {
            user.updateUserLevel();
        } else if(currentLevel == 냥린이 && userLevelScore >= MIN_POINT_FOR_GOLD) {
            user.updateUserLevel();
        } else if(currentLevel == 대장냥 && userLevelScore >= MIN_POINT_FOR_PLATINUM) {
            user.updateUserLevel();
        }
        currentLevel = user.getUserLevel();
        if(currentLevel == 아깽이) {
            user.setScoreLeft(20 - userLevelScore);
        } else if (currentLevel == 냥린이) {
            user.setScoreLeft(50 - userLevelScore);
        } else if (currentLevel == 대장냥) {
            user.setScoreLeft(100 - userLevelScore);
        }
        userRepository.save(user);
        return userLevelScore;
    }

}
