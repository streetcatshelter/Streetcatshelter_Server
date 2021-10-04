package streetcatshelter.discatch.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import streetcatshelter.discatch.dto.requestDto.CatDetailRequestDto;
import streetcatshelter.discatch.repository.CatDetailRepository;
import streetcatshelter.discatch.repository.CatImageRepository;

import java.util.ArrayList;
import java.util.List;

class CatDetailTest {

    @Autowired
    private CatImageRepository catImageRepository;
    private CatDetailRepository catDetailRepository;

    @Test
    void catDetailImageTest(){
        List<String> imgs = new ArrayList<>();
        imgs.add("dsjfaolidsf");
        CatDetailRequestDto catDetailRequestDto = new CatDetailRequestDto();
        catDetailRequestDto.setCatImages(imgs);

    }

}