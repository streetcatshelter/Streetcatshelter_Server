package streetcatshelter.discatch.dto.requestDto;

import lombok.Getter;

import java.util.List;

@Getter
public class CatRequestDto {
    private String username;
    private String catName;
    private String location;
    private String catImage;
    private String neutering;
    private List<String> catTag;
    private double latitude;
    private double longitude;
}
