package streetcatshelter.discatch.domain.cat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.cat.dto.requestdto.CatDetailRequestDto;
import streetcatshelter.discatch.domain.cat.dto.requestdto.CatRequestDto;
import streetcatshelter.discatch.domain.cat.dto.responsedto.*;
import streetcatshelter.discatch.dto.requestDto.CommentRequestDto;
import streetcatshelter.discatch.dto.responseDto.*;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.domain.cat.service.CatDetailService;
import streetcatshelter.discatch.domain.cat.service.CatService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CatController {

    private final CatService catService;
    private final CatDetailService catDetailService;

    @GetMapping("/cat/{location}")
    public List<CatResponseDto> getCatByLocation(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @PathVariable String location) {
        return catService.getCatByLocation(page, size, location);
    }

    @GetMapping("/cat/comment/{catId}")
    public List<CommentResponseDto> getCatComment(@PathVariable Long catId,
                              @RequestParam int page,
                              @RequestParam int size){

        return catService.getCatComment(catId,page,size);
    }

    @PostMapping("/cat/create")
    public void createCat(@RequestBody CatRequestDto requestDto,
                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        catService.createCat(requestDto, userPrincipal);
    }

    @PostMapping("/cat/detail/{catId}")
    public void createCatDetail(@RequestBody CatDetailRequestDto requestDto,
                                @PathVariable Long catId,
                                @AuthenticationPrincipal UserPrincipal userPrincipal) {

        catDetailService.createCatDetail(requestDto, catId, userPrincipal);
        // osiv 끄면 쿼리 2방??
    }

    @GetMapping("/cat/gallery/{catId}")
    public List<CatGalleryResponseDto> getCatPhotoByCat(@PathVariable Long catId,
                                                        @RequestParam int page,
                                                        @RequestParam int size) {
        return catService.getCatPhotos(page,size,catId);
    }

    @PostMapping("/cat/detail/comment/{catDetailId}")
    public void createCatDetailComment(@PathVariable Long catDetailId,
                                       @RequestBody CommentRequestDto commentRequestDto,
                                       @AuthenticationPrincipal UserPrincipal userPrincipal){
        catService.createDetailComment(catDetailId,commentRequestDto, userPrincipal.getUser());
    }

    @PostMapping("/cat/comment/{catId}")
    public void createCatComment(@PathVariable Long catId,
                                 @RequestBody CommentRequestDto commentRequestDto,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal){
        catService.createComment(catId,commentRequestDto, userPrincipal.getUser());
    }


    @GetMapping("/cat/detail/comment/{catDetailId}")
    public List<CommentResponseDto> getCatCommentByCat(@PathVariable Long catDetailId,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        return catService.getCatCommentByCatDetail(catDetailId,page,size);
    }

    @GetMapping("/cat/diary/{catId}")
    public List<CatDiaryResponseDto> getCatDiaryByCat(@PathVariable Long catId,
                                                      @RequestParam int page,
                                                      @RequestParam int size) {
        return catService.getCatDiaryByCat(catId,page,size);
    }

    @GetMapping("/cat/detail/{catDetailId}")
    public CatDetailResponseDto getCatDetail(@PathVariable Long catDetailId,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return catService.getCatDetail(catDetailId, userPrincipal.getUser());
    }

    @PostMapping("/cat/like/{catId}")
    public String likeCat(@PathVariable Long catId,
                                    @AuthenticationPrincipal UserPrincipal userPrincipal){
        return catService.addlike(catId,userPrincipal.getUser());
    }

    @PostMapping("/cat/detail/like/{catDetailId}")
    public Map<String,Long> likeCatDetail(@PathVariable Long catDetailId,
                                          @AuthenticationPrincipal UserPrincipal userPrincipal){
        return catDetailService.addlike(catDetailId,userPrincipal.getUser());
    }

    @DeleteMapping("/cat/detail/{catDetailId}")
    public void deleteCatDetail(@PathVariable Long catDetailId,
                                @AuthenticationPrincipal UserPrincipal userPrincipal){
        catDetailService.deleteCatDetail(catDetailId, userPrincipal.getUser());
    }

    @DeleteMapping("/cat/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId,
                              @AuthenticationPrincipal UserPrincipal userPrincipal){
        catService.deleteComment(commentId,userPrincipal.getUser());

    }

    @GetMapping("/cat/calender/{catId}")
    public Result<CalendarResponseDto> getCalender(@PathVariable Long catId,
                                                   @RequestParam int year,
                                                   @RequestParam int month){
        List<CalendarResponseDto> calender = catDetailService.getCalender(catId, year, month);
        Result<CalendarResponseDto> result = new Result<>();
        result.setDate(calender);
        return result;



    }

}
