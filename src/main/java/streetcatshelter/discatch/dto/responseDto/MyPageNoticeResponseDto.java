package streetcatshelter.discatch.dto.responseDto;

import lombok.Getter;
import streetcatshelter.discatch.domain.Notice;

@Getter
public class MyPageNoticeResponseDto {
    private Notice notice;

    public MyPageNoticeResponseDto(Notice notice) {
        this.notice = notice;
    }
}
