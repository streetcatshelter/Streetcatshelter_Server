package streetcatshelter.discatch.domain.cat.dto.responsedto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Builder
public class CatDetailResponseDto {

    private boolean water;
    private boolean food;
    private boolean snack;
    private boolean isUserLiked;
    private Long catDetailId;
    private Long likeCnt;
    private Long viewCnt;
    private Long commentCnt;
    private String diary;
    private String createdAt;
    private List<String> catTags;
    private List<String> catImages;

}
