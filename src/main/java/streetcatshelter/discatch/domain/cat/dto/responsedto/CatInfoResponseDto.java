package streetcatshelter.discatch.domain.cat.dto.responsedto;


import lombok.Data;
import streetcatshelter.discatch.domain.cat.domain.CatTag;

import javax.persistence.Column;
import java.util.List;

@Data
public class CatInfoResponseDto {

    private String catName;
    private String location;
    private String catImage;
    private String neutering;
    private List<CatTag> catTagList;


}
