package streetcatshelter.discatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.Cat;
import streetcatshelter.discatch.domain.CatDetail;
import streetcatshelter.discatch.domain.CatImage;
import streetcatshelter.discatch.domain.Comment;
import streetcatshelter.discatch.dto.CatDetailRequestDto;
import streetcatshelter.discatch.dto.CatRequestDto;
import streetcatshelter.discatch.dto.CommentRequestDto;
import streetcatshelter.discatch.dto.response.CatDetailResponseDto;
import streetcatshelter.discatch.dto.response.CatDiaryResponseDto;
import streetcatshelter.discatch.dto.response.CatGalleryResponseDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
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

    @PostMapping("/cat/detailCreate/{catId}")
    public void createCatDetail(@RequestBody CatDetailRequestDto requestDto, @PathVariable Long catId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        catDetailService.createCatDetail(requestDto, catId, userPrincipal);
    }

    @GetMapping("/cat/gallery/{catId}")
    public List<CatGalleryResponseDto> getCatPhotoByCat(@PathVariable Long catId, @RequestParam int page, @RequestParam int size) {
        return catService.getCatPhotos(page,size,catId);
    }

    @PostMapping("/cat/detail/comment/{catDetailId}")
    public void createCatDetailComment(@PathVariable Long catDetailId,@RequestBody CommentRequestDto commentRequestDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        catService.createDetailComment(catDetailId,commentRequestDto, userPrincipal.getUser());
    }

    @PostMapping("/cat/comment/{catId}")
    public void createCatComment(@PathVariable Long catId,@RequestBody CommentRequestDto commentRequestDto,@AuthenticationPrincipal UserPrincipal userPrincipal){
        catService.createComment(catId,commentRequestDto, userPrincipal.getUser());
    }


    @GetMapping("/cat/detail/comment/{catDetailId}")
    public Page<Comment> getCatCommentByCat(@PathVariable Long catDetailId, @RequestParam int page, @RequestParam int size) {
        return catService.getCatCommentByCatDetail(catDetailId,page,size);
    }

    @GetMapping("/cat/diary/{catId}")
    public List<CatDiaryResponseDto> getCatDiaryByCat(@PathVariable Long catId, @RequestParam int page, @RequestParam int size) {
        return catService.getCatDiaryByCat(catId,page,size);
    }

    @GetMapping("/cat/catDetail/{catDetailId}")
    public CatDetailResponseDto getCatDetail(@PathVariable Long catDetailId) {
        return catService.getCatDetail(catDetailId);
    }



//    @GetMapping("/cat/calendar/{catId}")
//    public List<CatDetail> getCatCalendarByCat(@PathVariable Long catId) {
//
//    }



    //캘린더  집사일기(좋아요 조회수 코멘트수)  고양이사진   이렇게 세개 디비로 나눌까? db검색 속도가 빨라질려나?
    // 차라리 responseDto를 이용해서 필요한것만 내려주는 방식이 어떨지.. 나중에 고민 ㄱ



}
