package streetcatshelter.discatch.dto;

import lombok.Getter;

@Getter
public class CatDetailRequestDto {
    private boolean water;
    private boolean food;
    private boolean snack;
    private String catDetailImage;
    private String diary;
    private double latitude;
    private double longitude;
}
