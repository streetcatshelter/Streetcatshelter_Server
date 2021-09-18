package streetcatshelter.discatch.dto;

import lombok.Getter;
import streetcatshelter.discatch.domain.CatTag;
import streetcatshelter.discatch.domain.Neutering;

import java.awt.*;
import java.util.List;

@Getter
public class CatRequestDto {
    private Long catId;
    private Point location;
    private List<CatTag> catTags;
    private String catName;
    private Neutering neutering;
    private String catImage;
}
