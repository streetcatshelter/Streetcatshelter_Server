package streetcatshelter.discatch.dto.responseDto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


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

}
