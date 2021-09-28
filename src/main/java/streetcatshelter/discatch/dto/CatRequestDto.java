package streetcatshelter.discatch.dto;

import lombok.Getter;
import streetcatshelter.discatch.domain.Neutering;

import java.util.List;

@Getter
public class CatRequestDto {
    private String username;
    private String catName;
    private String location;
    private String catImage;
    private Neutering neutering;
    private List<String> catTag;
    private double latitude;
    private double longitude;

}
