package streetcatshelter.discatch.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import streetcatshelter.discatch.domain.cat.dto.requestdto.CatDetailRequestDto;
import streetcatshelter.discatch.domain.cat.repository.CatCalenderRepository;
import streetcatshelter.discatch.domain.cat.repository.CatDetailRepository;
import streetcatshelter.discatch.domain.cat.repository.CatImageRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class CatDetailTest {

    @Autowired
    private CatImageRepository catImageRepository;
    private CatDetailRepository catDetailRepository;
    private CatCalenderRepository catCalenderRepository;
    @Test
    void catDetailImageTest(){
        List<String> imgs = new ArrayList<>();
        imgs.add("dsjfaolidsf");
        CatDetailRequestDto catDetailRequestDto = new CatDetailRequestDto();
        catDetailRequestDto.setCatImages(imgs);

    }


    @Test
    void callender(){
        LocalDate localDate = LocalDate.of(2021,10,15);
        LocalDate localDate1 = LocalDate.of(2021,10,1);
        LocalDate localDate2 = LocalDate.of(2021,10,LocalDate.now().lengthOfMonth());

        for(LocalDate date = localDate1; date.isBefore(localDate2.plusDays(1)); date = date.plusDays(1)){
            System.out.println(date);

        }
        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.lengthOfMonth());
        System.out.println(localDate.getMonthValue());

    }
}