package streetcatshelter.discatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import streetcatshelter.discatch.domain.Notice;
import streetcatshelter.discatch.dto.requestDto.UserInformationRequestDto;
import streetcatshelter.discatch.dto.responseDto.MyPageCatsResponseDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.service.MyPageService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage/mycat")
    public ArrayList<MyPageCatsResponseDto> findAllCats(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return myPageService.findAllCats(userPrincipal);
    }

    @GetMapping("/mypage/notice")
    public List<Notice> getAllNotices() {
        return myPageService.getAllNotices();
    }

    @PutMapping("/mypage/user/information")
    public String putUserInformation(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                               @RequestBody UserInformationRequestDto requestDto) {
        return myPageService.putUserInformation(userPrincipal, requestDto);
    }

/*    @GetMapping("/mypage/calendar")
    public MyPageCalendarResponseDto myAllActivities(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return myPageService.myAllActivities(userPrincipal);
    }*/
}
