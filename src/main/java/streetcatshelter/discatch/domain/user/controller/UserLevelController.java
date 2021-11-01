package streetcatshelter.discatch.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import streetcatshelter.discatch.domain.user.service.UserLevelService;

@RestController
@RequiredArgsConstructor
public class UserLevelController {
    private final UserLevelService userLevelService;

    @GetMapping("/leveluptest")
    public void testLogin(){
        userLevelService.upgradeLevels();
    }
}
