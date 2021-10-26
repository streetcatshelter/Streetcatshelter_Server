package streetcatshelter.discatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.service.CommunityLikeitService;

@RequiredArgsConstructor
@RestController
public class CommunityLikeitController {

    private final CommunityLikeitService communityLikeitService;

    @PostMapping("/community/likeit/{communityId}")
    public String CreateAndDeleteCommunityLikeit(@PathVariable Long communityId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return communityLikeitService.CreateAndDeleteCommunityLikeit(communityId, userPrincipal);
    }
}
