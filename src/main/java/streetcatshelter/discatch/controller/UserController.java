package streetcatshelter.discatch.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;

@RestController
public class UserController {

    @GetMapping("/logintest")
    public void testLogin(@AuthenticationPrincipal UserPrincipal userPrincipal){
        System.out.println("================="+userPrincipal.getUser().getUsername());
    }
}
