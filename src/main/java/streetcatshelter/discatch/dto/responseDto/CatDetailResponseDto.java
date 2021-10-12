package streetcatshelter.discatch.dto.responseDto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.CatImage;

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
    private Long likeCnt;
    private Long viewCnt;
    private Long commentCnt;
    private String diary;
    private LocalDateTime createdAt;
    private List<String> catTags;
    private List<String> catImages;

}
