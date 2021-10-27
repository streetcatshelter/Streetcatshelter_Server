package streetcatshelter.discatch.domain.community.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CommunityRequestDto {
    private String category;
    private String title;
    private String username;
    private String contents;
    private String location;
    private List<String> image;
}
