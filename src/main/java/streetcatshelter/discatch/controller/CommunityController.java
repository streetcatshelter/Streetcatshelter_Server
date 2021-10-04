package streetcatshelter.discatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.Community;
import streetcatshelter.discatch.dto.requestDto.CommentRequestDto;
import streetcatshelter.discatch.dto.requestDto.CommunityRequestDto;
import streetcatshelter.discatch.dto.responseDto.CommunityResponseDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.service.CommunityService;

@RequiredArgsConstructor
@RestController
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/community/category/{category}")
    public CommunityResponseDto getCommunityByCategory(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam ("location") String location,
            @PathVariable String category,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return communityService.getCommunityByCategory(page, size, category, location, userPrincipal);
    }

    //detail page 들어갈떄 쓰는 api
    @GetMapping("/community/{communityId}")
    public CommunityResponseDto getCommunityById(@PathVariable Long communityId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return communityService.getCommunityById(communityId, userPrincipal);
    }

    @PostMapping("/community/create")
    public void createCommunity(@RequestBody CommunityRequestDto requestDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        communityService.createCommunity(requestDto, userPrincipal);
    }


    @PutMapping("/community/{communityId}")
    public Community updateCommunity(@PathVariable Long communityId, @RequestBody CommunityRequestDto requestDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return communityService.update(communityId, requestDto, userPrincipal);
    }

    @DeleteMapping("/community/{communityId}")
    public void deleteCommunity(@PathVariable Long communityId) {
        communityService.delete(communityId);
    }

    //커뮤니티 코멘트들 다 모아놓음음
    @PostMapping("/community/comment/{communityId}")
    public void createComment(@PathVariable Long communityId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        communityService.createComment(communityId, requestDto, userPrincipal);
    }

    @PutMapping("/community/comment/{commentId}")
    public void updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        communityService.updateComment(commentId, requestDto, userPrincipal);
    }

    @DeleteMapping("/community/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        communityService.deleteComment(commentId, userPrincipal);
    }
}
