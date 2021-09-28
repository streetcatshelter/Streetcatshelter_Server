package streetcatshelter.discatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.Cat;
import streetcatshelter.discatch.domain.CatDetail;
import streetcatshelter.discatch.domain.Comment;
import streetcatshelter.discatch.dto.CatDetailRequestDto;
import streetcatshelter.discatch.dto.CatRequestDto;
import streetcatshelter.discatch.service.CatDetailService;
import streetcatshelter.discatch.service.CatService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CatController {

    private final CatService catService;
    private final CatDetailService catDetailService;

    @GetMapping("/cat/{location}")
    public Page<Cat> getCatByLocation(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @PathVariable String location) {
        return catService.getCatByLocation(page, size, location);
    }

    @PostMapping("/cat/create")
    public void createCat(@RequestBody CatRequestDto requestDto) {
        catService.createCat(requestDto);
    }

    @PostMapping("/catDetail/create/{catId}")
    public void createCatDetail(@RequestBody CatDetailRequestDto requestDto, @PathVariable Long catId, @AuthenticationPrincipal UserDetails userDetails) {
        catDetailService.createCatDetail(requestDto,catId, userDetails);
    }
    @GetMapping("/cat/calendar/{catId}")
    public List<CatDetail> getCatCalendarByCat(@PathVariable Long catId) {
        return catService.getCatDetailByCat(catId);
    }

    @GetMapping("/cat/comment/{catId}")
    public List<Comment> getCatCommentByCat(@PathVariable Long catId) {
        return catService.getCatCommentByCat(catId);
    }
    //캘린더  집사일기(좋아요 조회수 코멘트수)  고양이사진   이렇게 세개 디비로 나눌까? db검색 속도가 빨라질려나?
    // 차라리 responseDto를 이용해서 필요한것만 내려주는 방식이 어떨지.. 나중에 고민 ㄱ
    @GetMapping("/cat/diary/{catId}")
    public List<CatDetail> getCatDiaryByCat(@PathVariable Long catId) {
        return catService.getCatDetailByCat(catId);
    }

    @GetMapping("/cat/photo/{catId}")
    public List<CatDetail> getCatPhotoByCat(@PathVariable Long catId) {
        return catService.getCatDetailByCat(catId);
    }

    @GetMapping("/cat/catDetail/{catDetailId}")
    public Optional<CatDetail> getCatDetail(@PathVariable Long catDetailId) {
        return catService.getCatDetail(catDetailId);
    }

}
