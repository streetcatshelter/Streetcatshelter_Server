package streetcatshelter.discatch.domain.mypage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.domain.mypage.dto.*;
import streetcatshelter.discatch.domain.mypage.service.MyPageService;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.domain.user.dto.UserInformationRequestDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage/mycat")
    public List<MyPageCatsResponseDto> findAllCats(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return myPageService.findAllCats(userPrincipal);
    }

    @GetMapping("/mypage/notice")
    public List<MyPageNoticeResponseDto> getAllNotices() {
        return myPageService.getAllNotices();
    }

    @GetMapping("/mypage/notice/{noticeId}")
    public MyPageNoticeResponseDto getNotice(@PathVariable Long noticeId) {
        return myPageService.getNotice(noticeId);
    }

    @PutMapping("/mypage/user/information")
    public void putUserInformation(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                               @RequestBody UserInformationRequestDto requestDto) {
        myPageService.putUserInformation(userPrincipal, requestDto);
    }

    @GetMapping("/mypage/user/information")
    public MyPageUserInformationResponseDto getUserInformation(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return myPageService.getUserInformation(userPrincipal);
    }

    @GetMapping("/mypage/calendar")
    public List<MyPageAllCalendarResponseDto> myAllActivities(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam int year, @RequestParam int month) {
        return myPageService.myAllActivities(userPrincipal, year, month);
    }

    @GetMapping("/mypage/calendar/day/{day}")
    public List<MyPageCalendarResponseDto> myActivity(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam int year, @RequestParam int month, @PathVariable int day) {
        return myPageService.myActivity(userPrincipal, year, month, day);
    }
}
