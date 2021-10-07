package streetcatshelter.discatch.dto.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import streetcatshelter.discatch.domain.Cat;

@Getter
@Setter
@Builder
public class CatResponseDto {
    private Cat cat ;

    public CatResponseDto(Cat cat) {
        this.cat = cat;
    }
}
