package streetcatshelter.discatch.domain.mypage.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class MyPageNoticeResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String writer;
    private String createdAt;
    private String modifiedAt;
}
