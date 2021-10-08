package streetcatshelter.discatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import streetcatshelter.discatch.dto.requestDto.UserInformationRequestDto;
import streetcatshelter.discatch.dto.responseDto.MyPageCatsResponseDto;
import streetcatshelter.discatch.dto.responseDto.MyPageNoticeResponseDto;
import streetcatshelter.discatch.dto.responseDto.MyPageUserInformationResponseDto;
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
    public MyPageUserInformationResponseDto putUserInformation(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                               @RequestBody UserInformationRequestDto requestDto) {
        return myPageService.putUserInformation(userPrincipal, requestDto);
    }

/*    @GetMapping("/mypage/calendar")
    public MyPageCalendarResponseDto myAllActivities(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return myPageService.myAllActivities(userPrincipal);
    }*/
}
