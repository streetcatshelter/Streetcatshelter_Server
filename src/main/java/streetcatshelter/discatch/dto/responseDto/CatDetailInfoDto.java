package streetcatshelter.discatch.dto.responseDto;

import streetcatshelter.discatch.domain.CatTag;

import java.util.List;

public class CatDetailInfoDto {
    private String catImage;
    private boolean snack;
    private boolean feed;
    private boolean water;
    private String diary;
    private List<CatTag> catTagList;




}


//{
//catImage : "고양이 사진",
//snack : boolean,
//feed : boolean,
//water : boolean,
//diary : "집사일기",
//List<catTag>: "캣태그"
//latitude : "위도"
//longitude : "경도"
//}