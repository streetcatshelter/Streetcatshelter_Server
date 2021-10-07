package streetcatshelter.discatch.dto.responseDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPageCatsResponseDto {

    private LocalDateTime lastActivity;
    private LocalDateTime myActivity;
    private String catName;
    private String catImage;
    private int cntComment;
    private int cntCatDetail;
    private Long catId;

    public MyPageCatsResponseDto(LocalDateTime lastActivity, LocalDateTime myActivity, String catName, String catImage, Long catId,  int cntComment, int cntCatDetail) {
        this.catImage = catImage;
        this.catName = catName;
        this.cntCatDetail = cntCatDetail;
        this.cntComment = cntComment;
        this.lastActivity = lastActivity;
        this.myActivity = myActivity;
        this.catId = catId;
    }
}
