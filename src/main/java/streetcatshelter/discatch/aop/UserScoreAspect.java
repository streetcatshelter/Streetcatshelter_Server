package streetcatshelter.discatch.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.domain.user.domain.UserLevel;
import streetcatshelter.discatch.domain.user.repository.UserRepository;

import java.util.Arrays;

import static streetcatshelter.discatch.domain.user.domain.UserLevel.*;

@Aspect
@Component
@RequiredArgsConstructor
public class UserScoreAspect {
    private final UserRepository userRepository;

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
        int MIN_POINT_FOR_SILVER = 100;
        int MIN_POINT_FOR_GOLD = 300;
        int MIN_POINT_FOR_PLATINUM = 500;
        int MIN_POINT_FOR_DIAMOND = 1000;

        //기준 점수를 변경하는 경우 db를 하나씩 다 검사해서 조정해주는 작업은 미친짓이므로..
        //기존에 썼던 이 방법 즉 db를 다 검색해서 전체 점수를 구하는 방법을 이용해서 업데이트마다 이용하느 방법이 좋을것같다.

/*        int userLevelScore;
        int A = catRepository.countAllByUser_UserSeq(user.getUserSeq());
        int B = catDetailRepository.countAllByUser_UserSeq(user.getUserSeq());
        int C = commentRepository.countAllByUser_UserSeq(user.getUserSeq());
        int D = communityRepository.countAllByUser_UserSeq(user.getUserSeq());
        userLevelScore = (A*5 + B*2 + C*2 + D*5);
        user.setScore(userLevelScore);*/

        if(joinPoint.toShortString().contains("createCat")) {
            user.setScore(user.getScore() + 5);
        } else if(joinPoint.toShortString().contains("createCatDetail")) {
            user.setScore(user.getScore() + 2);
        } else if(joinPoint.toShortString().contains("deleteCatDetail")) {
            user.setScore(user.getScore() - 2);
        } else if(joinPoint.toShortString().contains("createComment")) {
            user.setScore(user.getScore() + 2);
        } else if(joinPoint.toShortString().contains("createDetailComment")) {
            user.setScore(user.getScore() + 2);
        } else if(joinPoint.toShortString().contains("deleteComment")) {
            user.setScore(user.getScore() - 2);
        } else if(joinPoint.toShortString().contains("createCommunity")) {
            user.setScore(user.getScore() + 5);
        } else if(joinPoint.toShortString().contains("deleteCommunity")) {
            user.setScore(user.getScore() - 5);
        }

        int userLevelScore = user.getScore();



        if(currentLevel == 아깽이 && userLevelScore >= MIN_POINT_FOR_SILVER) {
            user.updateUserLevel();
        } else if(currentLevel == 냥린이 && userLevelScore >= MIN_POINT_FOR_GOLD) {
            user.updateUserLevel();
        } else if(currentLevel == 대장냥 && userLevelScore >= MIN_POINT_FOR_PLATINUM) {
            user.updateUserLevel();
        } else if(currentLevel == 프로집사 && userLevelScore >= MIN_POINT_FOR_DIAMOND) {
            user.updateUserLevel();
        }

        currentLevel = user.getUserLevel();
        if(currentLevel == 아깽이) {
            user.setScoreLeft(100 - userLevelScore);
        } else if (currentLevel == 냥린이) {
            user.setScoreLeft(300 - userLevelScore);
        } else if (currentLevel == 대장냥) {
            user.setScoreLeft(500 - userLevelScore);
        } else if (currentLevel == 프로집사) {
            user.setScoreLeft(1000 - userLevelScore);
        } else if (currentLevel == 고양이신) {
            user.setScoreLeft(0);
        }

        userRepository.save(user);
        return userLevelScore;
    }

}
