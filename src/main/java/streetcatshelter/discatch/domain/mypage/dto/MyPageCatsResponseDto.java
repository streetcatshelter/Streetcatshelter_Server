package streetcatshelter.discatch.domain.mypage.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyPageCatsResponseDto {

    private String lastActivity;
    private String myActivity;
    private String catName;
    private String catImage;
    private int cntComment;
    private int cntCatDetail;
    private Long catId;

    public MyPageCatsResponseDto(String lastActivity, String myActivity, String catName, String catImage, Long catId,  int cntComment, int cntCatDetail) {
        this.catImage = catImage;
        this.catName = catName;
        this.cntCatDetail = cntCatDetail;
        this.cntComment = cntComment;
        this.lastActivity = lastActivity;
        this.myActivity = myActivity;
        this.catId = catId;
    }
}
