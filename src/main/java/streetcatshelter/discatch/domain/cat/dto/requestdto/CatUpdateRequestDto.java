package streetcatshelter.discatch.domain.cat.dto.requestdto;

import lombok.Data;

import java.util.List;

@Data
public class CatUpdateRequestDto {
    private String catName;
    private String catImage;
    private String neutering;
    private List<String> catTag;
}
