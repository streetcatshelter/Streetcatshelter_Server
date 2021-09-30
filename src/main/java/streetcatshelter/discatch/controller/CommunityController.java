package streetcatshelter.discatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.Community;
import streetcatshelter.discatch.dto.CommentRequestDto;
import streetcatshelter.discatch.dto.CommunityRequestDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.service.CommunityService;

@RequiredArgsConstructor
@RestController
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/community/category/{category}")
    public Page<Community> getCommunityByCategory(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam ("location") String location,
            @PathVariable String category) {
        return communityService.getCommunityByCategory(page, size, category, location);
    }

    //detail page 들어갈떄 쓰는 api
    @GetMapping("/community/{communityId}")
    public Community getCommunityById(@PathVariable Long communityId) {
        return communityService.getCommunityById(communityId);
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
