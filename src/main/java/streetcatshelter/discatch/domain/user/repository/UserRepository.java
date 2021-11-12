package streetcatshelter.discatch.domain.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userID);
    User findByUserSeq(Long userSeq);
    User findByNickname(String nickname);
}
