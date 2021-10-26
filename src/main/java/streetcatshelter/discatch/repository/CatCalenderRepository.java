package streetcatshelter.discatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import streetcatshelter.discatch.domain.Cat;
import streetcatshelter.discatch.domain.CatCalender;
import streetcatshelter.discatch.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface CatCalenderRepository extends JpaRepository<CatCalender,Long> {


    List<CatCalender> findALLByLocalDateBetweenAndCatId(LocalDate localDate1, LocalDate localDate2, Long CatId);
    List<CatCalender> findALLByLocalDateBetweenAndUser(LocalDate localDate1, LocalDate localDate2, User user);

    CatCalender findByLocalDate(LocalDate localDate);
    CatCalender findByLocalDateAndCatAndUser(LocalDate localDate, Cat cat, User user);
    List<CatCalender> findByLocalDateAndUser(LocalDate localDate, User user);
}
