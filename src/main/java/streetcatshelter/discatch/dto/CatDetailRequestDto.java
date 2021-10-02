package streetcatshelter.discatch.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CatDetailRequestDto {
    private boolean water;
    private boolean food;
    private boolean snack;
    private List<String> catImages;
    private List<String> catTags;
    private String diary;
    private double latitude;
    private double longitude;
}
