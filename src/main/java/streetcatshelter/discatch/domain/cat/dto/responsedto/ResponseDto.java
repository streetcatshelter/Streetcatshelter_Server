package streetcatshelter.discatch.domain.cat.dto.responsedto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseDto {
    private Boolean isLast;
    private Object responses;
}
