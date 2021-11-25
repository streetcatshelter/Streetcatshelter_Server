package streetcatshelter.discatch.domain.cat.dto.requestdto;


import lombok.Data;

import java.util.List;

@Data
public class CatDetailUpdateRequestDto {

    private boolean water;
    private boolean food;
    private boolean snack;
    private List<String> catImages;
    private List<String> catTags;
    private String diary;

}
