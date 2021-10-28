package streetcatshelter.discatch.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.cat.repository.CatDetailRepository;
import streetcatshelter.discatch.domain.cat.repository.CatRepository;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.domain.user.domain.UserLevel;
import streetcatshelter.discatch.domain.user.repository.UserRepository;
import streetcatshelter.discatch.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLevelService {

    private final UserRepository userRepository;
    private final CatRepository catRepository;
    private final CommentRepository commentRepository;
    private final CatDetailRepository catDetailRepository;

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void upgradeLevels(){
        List<User> users = userRepository.findAll(); // 모든 유저 조회
        for(User user : users){ // 유저하나하나 조회
            if(canUpgradeLevel(user)) // 업그레이드 가능여부
                user.updateUserLevel(); // 업그레이드
        }
    }

    private boolean canUpgradeLevel(User user){// 레벨 업그레이드 가능한지 여부를 체크하는 함수이다.
        UserLevel currentLevel = user.getUserLevel();
        if(currentLevel == null) {
            user.setUserLevel(UserLevel.아깽이);
            return true;
        }
        int userLevelScore;
        int A = catRepository.countAllByUser_UserSeq(user.getUserSeq());
        int B = catDetailRepository.countAllByUser_UserSeq(user.getUserSeq());
        int C = commentRepository.countAllByUser_UserSeq(user.getUserSeq());
        userLevelScore = (A*5 + B*2 + C*2);
        user.setScore(userLevelScore);
        int MIN_POINT_FOR_SILVER = 20;
        int MIN_POINT_FOR_GOLD = 50;
        int MIN_POINT_FOR_PLATINUM =100;
        switch (currentLevel){
            case 아깽이: return (userLevelScore >= MIN_POINT_FOR_SILVER); //로그인한횟수
            case 냥린이: return (userLevelScore >= MIN_POINT_FOR_GOLD);
            case 대장냥: return (userLevelScore >= MIN_POINT_FOR_PLATINUM);
            case 프로집사: return false;
            default: throw new IllegalArgumentException("Unknown Level");
        }
    }
}
