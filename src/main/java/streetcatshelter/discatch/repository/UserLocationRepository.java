package streetcatshelter.discatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.UserLocation;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    void deleteAllByUser_UserSeq(Long userSeq);
}
