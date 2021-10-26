package streetcatshelter.discatch.domain.cat.dto.responsedto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CatDiaryResponseDto {

    private LocalDateTime createdAt;
    private Long catDetailId;
    private String diary;
    private Long likeCnt;
    private Long viewCnt;
    private Long commentCnt;
    private Long userId;
    private String username;
    private String profileImageUrl;

}
