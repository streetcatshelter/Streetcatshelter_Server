package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.*;
import streetcatshelter.discatch.dto.requestDto.UserInformationRequestDto;
import streetcatshelter.discatch.dto.responseDto.MyPageCalendarResponseDto;
import streetcatshelter.discatch.dto.responseDto.MyPageCatsResponseDto;
import streetcatshelter.discatch.dto.responseDto.MyPageNoticeResponseDto;
import streetcatshelter.discatch.dto.responseDto.MyPageUserInformationResponseDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.repository.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final CatDetailRepository catDetailRepository;
    private final LikedRepository likedRepository;
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public List<MyPageCatsResponseDto> findAllCats(UserPrincipal userPrincipal) {
        Long userSeq = userPrincipal.getUser().getUserSeq();
        List<Liked> LikedList = likedRepository.findAllByUser_UserSeq(userSeq);
        List<MyPageCatsResponseDto> responseDtoList = new ArrayList<>();

        for(Liked liked: LikedList) {
            Cat cat = liked.getCat();
            CatDetail catDetail = catDetailRepository.findFirstByOrderByIdDesc();
            ArrayList<CatDetail> myCatDetailList = catDetailRepository.findAllByUser(userPrincipal.getUser());
            CatDetail myCatDetail = myCatDetailList.get(myCatDetailList.size() -1);
            LocalDateTime lastActivity = catDetail.getCreatedAt();
            LocalDateTime myActivity = myCatDetail.getCreatedAt();
            String catName = cat.getCatName();
            String catImage = cat.getCatImage();
            Long catId = cat.getId();
            int cntComment = commentRepository.countAllByUser_UserSeqAndCatId(userSeq, catId);
            int cntCatDetail = catDetailRepository.countAllByUser_UserSeqAndCatId(userSeq, catId);
            MyPageCatsResponseDto myPageCatsResponseDto = new MyPageCatsResponseDto(lastActivity, myActivity, catName, catImage, catId, cntComment, cntCatDetail);
            responseDtoList.add(myPageCatsResponseDto);
        }

        return responseDtoList;
    }


    public List<MyPageNoticeResponseDto> getAllNotices() {
        List<Notice> noticeList = noticeRepository.findAllByOrderByIdDesc();
        List<MyPageNoticeResponseDto> responseDtoList = new ArrayList<>();
        for(Notice notice : noticeList) {
            responseDtoList.add(MyPageNoticeResponseDto.builder()
                    .title(notice.getTitle())
                    .id(notice.getId())
                    .contents(notice.getContents())
                    .writer(notice.getWriter())
                    .modifiedAt(String.valueOf(notice.getModifiedAt()).replace('T',' '))
                    .createdAt(String.valueOf(notice.getCreatedAt()).replace('T',' '))
                    .build());
        }
        return responseDtoList;
    }

    public MyPageNoticeResponseDto getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 notice는 존재하지 않습니다"));
        return MyPageNoticeResponseDto.builder()
                .title(notice.getTitle())
                .id(notice.getId())
                .contents(notice.getContents())
                .writer(notice.getWriter())
                .modifiedAt(String.valueOf(notice.getModifiedAt()).replace('T',' '))
                .createdAt(String.valueOf(notice.getCreatedAt()).replace('T',' '))
                .build();
    }

    @Transactional
    public String putUserInformation(UserPrincipal userPrincipal, UserInformationRequestDto requestDto) {
        User user = userPrincipal.getUser();
        if(requestDto.getNickname() == null) {
            return "닉네임을 입력해주세요";
        } else if (requestDto.getLocation() == null && requestDto.getLocation2() == null && requestDto.getLocation3()==null) {
            return "최소 1곳 이상의 동네를 입력해주세요";
        } else {
            user.update(requestDto);
            userRepository.save(user);
            return "회원 정보 등록/수정이 완료되었습니다.";
        }
    }

    public MyPageUserInformationResponseDto getUserInformation(UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        LocalDateTime start = LocalDateTime.now().minusMonths(2);
        LocalDateTime end = LocalDateTime.now();
        int cntActivity = catDetailRepository.countAllByUserAndModifiedAtBetween(user, start, end);
        return MyPageUserInformationResponseDto.builder()
                .location(user.getLocation())
                .location2(user.getLocation2())
                .location3(user.getLocation3())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .userLevel(user.getUserLevel())
                .cntActivity(cntActivity)
                .build();
    }

    public List<MyPageCalendarResponseDto> myAllActivities(UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        LocalDateTime start = LocalDateTime.now().minusMonths(2);
        LocalDateTime end = LocalDateTime.now();
        ArrayList<CatDetail> catDetails = catDetailRepository.findAllByUserAndModifiedAtBetween(user, start, end);
        List<MyPageCalendarResponseDto> myPageCalendarResponseDtoList = new ArrayList<>();
        for(CatDetail catDetail : catDetails) {
            myPageCalendarResponseDtoList.add(MyPageCalendarResponseDto.builder()
                    .food(catDetail.isFood())
                    .snack(catDetail.isSnack())
                    .water(catDetail.isWater())
                    .createdAt(catDetail.getCreatedAt())
                    .modifiedAt(catDetail.getModifiedAt())
                    .build());
        }
        return myPageCalendarResponseDtoList;
    }


}
