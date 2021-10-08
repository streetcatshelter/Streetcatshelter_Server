package streetcatshelter.discatch.dto.responseDto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Builder
public class CatResponseDto {

    private Long id;
    private String catName;
    private String username;
    private String location;
    private double latitude;
    private double longitude;
    private String catImage;
    private String neutering;
    private int cntComment;
    private int cntLikeIt;
    private int cntView;
}
