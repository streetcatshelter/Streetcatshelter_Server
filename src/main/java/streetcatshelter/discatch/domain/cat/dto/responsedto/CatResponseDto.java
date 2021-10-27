package streetcatshelter.discatch.domain.cat.dto.responsedto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.cat.domain.CatTag;

import java.util.List;


@Getter
@Setter
@Builder
public class CatResponseDto {

    private Long catId;
    private String catName;
    private String catImage;
    private String neutering;
    private boolean userLiked;
    private List<CatTag> catTagList;

}
