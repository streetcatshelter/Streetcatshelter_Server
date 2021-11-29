package streetcatshelter.discatch.alarm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByUserRandomId(String userRandomId);
    void deleteAllByUserRandomId(String userRandomId);
}
