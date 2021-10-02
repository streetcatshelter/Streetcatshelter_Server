package streetcatshelter.discatch.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import streetcatshelter.discatch.domain.User;

@Getter
@Setter
@Builder
public class CatDiaryResponseDto {

    private User user;
    private Long catDetailId;
    private String diary;
    private Long likeCnt;
    private Long viewCnt;
    private Long commentCnt;

}
