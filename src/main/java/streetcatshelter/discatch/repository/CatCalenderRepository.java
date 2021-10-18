package streetcatshelter.discatch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import streetcatshelter.discatch.domain.CatCalender;


import java.time.LocalDate;
import java.util.List;

public interface CatCalenderRepository extends JpaRepository<CatCalender,Long> {


    List<CatCalender> findALLByLocalDateBetweenAndCatId(LocalDate localDate1, LocalDate localDate2, Long CatId);

    CatCalender findByLocalDate(LocalDate localDate);
}
