package streetcatshelter.discatch.dto.responseDto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.CatTag;

import java.util.List;


@Getter
@Setter
@Builder
public class CatResponseDto {

    private Long catId;
    private String catName;
    private String catImage;
    private String neutering;
    private List<CatTag> catTagList;

}
