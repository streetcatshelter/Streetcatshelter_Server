package streetcatshelter.discatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.Community;
import streetcatshelter.discatch.dto.CommunityRequestDto;
import streetcatshelter.discatch.service.CommunityService;

@RequiredArgsConstructor
@RestController
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/community/category/{location}")
    public Page<Community> getCommunityByCategory(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam ("category") String category,
            @PathVariable String location) {
        return communityService.getCommunityByCategory(page, size, category, location);
    }

    @PostMapping("/community/create")
    public void createCommunity(@RequestBody CommunityRequestDto requestDto) {
        communityService.createCommunity(requestDto);
    }

    @PutMapping("/community/{communityId}")
    public Community updateCommunity(@PathVariable Long communityId, @RequestBody CommunityRequestDto requestDto) {
        return communityService.update(communityId, requestDto);
    }

    @DeleteMapping("/community/{communityId}")
    public void deleteCommunity(@PathVariable Long communityId) {
        communityService.delete(communityId);
    }

}
