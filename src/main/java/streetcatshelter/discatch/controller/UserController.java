package streetcatshelter.discatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import streetcatshelter.discatch.dto.responseDto.LoginResponseDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/logintest")
    public void testLogin(@AuthenticationPrincipal UserPrincipal userPrincipal){
        System.out.println("================="+userPrincipal.getUser().getUsername());
    }

    @GetMapping("user/kakao/callback")
    public LoginResponseDto kakaoLogin(@RequestParam String code) {
        System.out.println(code);
        return userService.kakaoLogin(code);
    }

    @GetMapping("user/naver/callback")
    public String naverLogin(@RequestParam String code) {
        return userService.naverLogin(code);
    }

}
