package streetcatshelter.discatch.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.chat.dto.LoginResponseDto;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.domain.user.dto.UserInfoResponseDto;
import streetcatshelter.discatch.domain.user.service.UserService;

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
    public LoginResponseDto naverLogin(@RequestParam String code) {
        return userService.naverLogin(code);
    }

    @RequestMapping("user/google/callback")
    public LoginResponseDto googleLogin(@RequestParam String code) {
        return userService.googleLogin(code);
    }

    @GetMapping("/userinfo/{userRandomId}")
    public UserInfoResponseDto userInfo(@PathVariable String userRandomId){
        return userService.getUserInfo(userRandomId);
    }

}
