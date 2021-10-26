package streetcatshelter.discatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.dto.requestDto.UserInformationRequestDto;
import streetcatshelter.discatch.dto.responseDto.*;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.service.MyPageService;

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
    public Result<CalendarResponseDto> myAllActivities(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam int year, @RequestParam int month) {
        List<CalendarResponseDto> calender = myPageService.myAllActivities(userPrincipal, year, month);
        Result<CalendarResponseDto> result = new Result<>();
        result.setDate(calender);
        return result;
    }

    @GetMapping("/mypage/calendar/day/{day}")
    public List<MyPageCalendarResponseDto> myActivity(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam int year, @RequestParam int month, @PathVariable int day) {
        return myPageService.myActivity(userPrincipal, year, month, day);
    }
}
