package streetcatshelter.discatch.domain.cat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.cat.dto.requestdto.CatDetailRequestDto;
import streetcatshelter.discatch.domain.cat.dto.requestdto.CatDetailUpdateRequestDto;
import streetcatshelter.discatch.domain.cat.dto.requestdto.CatRequestDto;
import streetcatshelter.discatch.domain.cat.dto.requestdto.CatUpdateRequestDto;
import streetcatshelter.discatch.domain.cat.dto.responsedto.*;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.dto.requestDto.CommentRequestDto;
import streetcatshelter.discatch.dto.responseDto.*;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.domain.cat.service.CatDetailService;
import streetcatshelter.discatch.domain.cat.service.CatService;

import java.util.List;
import java.util.Map;

import static streetcatshelter.discatch.domain.user.service.UserService.userChecker;

@RequiredArgsConstructor
@RestController
public class CatController {

    private final CatService catService;
    private final CatDetailService catDetailService;

    @GetMapping("/cat/{location}")
    public List<CatResponseDto> getCatByLocation(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 @RequestParam("page") int page,
                                                 @RequestParam("size") int size,
                                                 @PathVariable String location) {
        userChecker(userPrincipal);

        return catService.getCatByLocation(page, size, location, userPrincipal.getUser());
    }

    @GetMapping("/cat/comment/{catId}")
    public List<CommentResponseDto> getCatComment(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                  @PathVariable Long catId,
                                                  @RequestParam int page,
                                                  @RequestParam int size) {
        userChecker(userPrincipal);

        return catService.getCatComment(catId, page, size, userPrincipal.getUser());
    }

    @PostMapping("/cat/create")
    public void createCat(@RequestBody CatRequestDto requestDto,
                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        userChecker(userPrincipal);
        catService.createCat(requestDto, userPrincipal.getUser());
    }

    @PostMapping("/cat/detail/{catId}")
    public void createCatDetail(@RequestBody CatDetailRequestDto requestDto,
                                @PathVariable Long catId,
                                @AuthenticationPrincipal UserPrincipal userPrincipal) {

        catDetailService.createCatDetail(requestDto, catId, userPrincipal.getUser());
    }

    @GetMapping("/cat/gallery/{catId}")
    public List<CatGalleryResponseDto> getCatPhotoByCat(@PathVariable Long catId,
                                                        @RequestParam int page,
                                                        @RequestParam int size) {
        return catService.getCatPhotos(page, size, catId);
    }

    @PostMapping("/cat/detail/comment/{catDetailId}")
    public void createCatDetailComment(@PathVariable Long catDetailId,
                                       @RequestBody CommentRequestDto commentRequestDto,
                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        catService.createDetailComment(catDetailId, commentRequestDto, userPrincipal.getUser());
    }

    @PostMapping("/cat/comment/{catId}")
    public void createCatComment(@PathVariable Long catId,
                                 @RequestBody CommentRequestDto commentRequestDto,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal) {
        catService.createComment(catId, commentRequestDto, userPrincipal.getUser());
    }


    @GetMapping("/cat/detail/comment/{catDetailId}")
    public List<CommentResponseDto> getCatCommentByCat(@AuthenticationPrincipal UserPrincipal userPrincipal,@PathVariable Long catDetailId,
                                                       @RequestParam int page,
                                                       @RequestParam int size) {
        return catService.getCatCommentByCatDetail(catDetailId, page, size, userPrincipal.getUser());
    }

    @GetMapping("/cat/diary/{catId}")
    public List<CatDiaryResponseDto> getCatDiaryByCat(@PathVariable Long catId,
                                                      @RequestParam int page,
                                                      @RequestParam int size) {
        return catService.getCatDiaryByCat(catId, page, size);
    }

    @GetMapping("/cat/detail/{catDetailId}")
    public CatDetailResponseDto getCatDetail(@PathVariable Long catDetailId,
                                             @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return catService.getCatDetail(catDetailId, userPrincipal.getUser());
    }

    @PostMapping("/cat/like/{catId}")
    public String likeCat(@PathVariable Long catId,
                          @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return catService.addlike(catId, userPrincipal.getUser());
    }

    @PostMapping("/cat/detail/like/{catDetailId}")
    public Map<String, Long> likeCatDetail(@PathVariable Long catDetailId,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return catDetailService.addlike(catDetailId, userPrincipal.getUser());
    }

    @DeleteMapping("/cat/detail/{catDetailId}")
    public void deleteCatDetail(@PathVariable Long catDetailId,
                                @AuthenticationPrincipal UserPrincipal userPrincipal) {
        catDetailService.deleteCatDetail(catDetailId, userPrincipal.getUser());
    }

    @DeleteMapping("/cat/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId,
                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        catService.deleteComment(commentId, userPrincipal.getUser());

    }

    @GetMapping("/cat/calender/{catId}")
    public Result<CalendarResponseDto> getCalender(@PathVariable Long catId,
                                                   @RequestParam int year,
                                                   @RequestParam int month) {
        List<CalendarResponseDto> calender = catDetailService.getCalender(catId, year, month);
        Result<CalendarResponseDto> result = new Result<>();
        result.setDate(calender);
        return result;


    }

    @GetMapping("cat/info/{catId}")
    public CatResponseDto getCatInfo(@PathVariable Long catId,
                                     @AuthenticationPrincipal UserPrincipal userPrincipal){

        userChecker(userPrincipal);
        return catService.getCatInfo(catId, userPrincipal.getUser());
    }

    @PutMapping("cat/{catId}")
    public void updateCatInfo(@PathVariable Long catId,
                              @AuthenticationPrincipal UserPrincipal userPrincipal,
                              @RequestBody CatUpdateRequestDto catUpdateRequestDto) {
        userChecker(userPrincipal);
        catService.updateCat(catId,catUpdateRequestDto,userPrincipal.getUser());
    }

    @PutMapping("cat/detail/{catDetailId}")
    public void updateCatDetail(@PathVariable Long catDetailId,
                                @AuthenticationPrincipal UserPrincipal userPrincipal,
                                @RequestBody CatDetailUpdateRequestDto catDetailUpdateRequestDto){

        userChecker(userPrincipal);

    }

}
