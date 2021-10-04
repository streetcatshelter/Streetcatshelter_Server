package streetcatshelter.discatch.dto.responseDto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import streetcatshelter.discatch.domain.User;

@Getter
@Setter
@Builder
public class CatDiaryResponseDto {

    private Long catDetailId;
    private String diary;
    private Long likeCnt;
    private Long viewCnt;
    private Long commentCnt;
    private Long userId;
    private String username;
    private String profileImageUrl;

}
