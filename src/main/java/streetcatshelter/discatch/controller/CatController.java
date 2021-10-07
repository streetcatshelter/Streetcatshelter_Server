package streetcatshelter.discatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.Cat;
import streetcatshelter.discatch.dto.requestDto.CatDetailRequestDto;
import streetcatshelter.discatch.dto.requestDto.CatRequestDto;
import streetcatshelter.discatch.dto.requestDto.CommentRequestDto;
import streetcatshelter.discatch.dto.responseDto.CatDetailResponseDto;
import streetcatshelter.discatch.dto.responseDto.CatDiaryResponseDto;
import streetcatshelter.discatch.dto.responseDto.CatGalleryResponseDto;
import streetcatshelter.discatch.dto.responseDto.CommentResponseDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.service.CatDetailService;
import streetcatshelter.discatch.service.CatService;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/cat/comment/{catId}")
    public List<CommentResponseDto> getCatComment(@PathVariable Long catId,
                              @RequestParam int page,
                              @RequestParam int size){

        return catService.getCatComment(catId,page,size);
    }

    @PostMapping("/cat/create")
    public void createCat(@RequestBody CatRequestDto requestDto) {
        catService.createCat(requestDto);
    }

    @PostMapping("/cat/detail/{catId}")
    public void createCatDetail(@RequestBody CatDetailRequestDto requestDto,
                                @PathVariable Long catId,
                                @AuthenticationPrincipal UserPrincipal userPrincipal) {
        catDetailService.createCatDetail(requestDto, catId, userPrincipal);
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

}
