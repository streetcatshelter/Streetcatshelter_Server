package streetcatshelter.discatch.dto.response;


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
    private Long likeCnt;
    private Long viewCnt;
    private Long commentCnt;
    private String diary;
}
