package streetcatshelter.discatch.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userID);
    User findByUserSeq(Long userSeq);
}
