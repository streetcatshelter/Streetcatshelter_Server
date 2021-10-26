package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.*;
import streetcatshelter.discatch.dto.requestDto.UserInformationRequestDto;
import streetcatshelter.discatch.dto.responseDto.*;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.repository.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
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
    private final CatCalenderRepository catCalenderRepository;
    private final UserLocationRepository userLocationRepository;

    public List<MyPageCatsResponseDto> findAllCats(UserPrincipal userPrincipal) {
        Long userSeq = userPrincipal.getUser().getUserSeq();
        List<Liked> LikedList = likedRepository.findAllByUser_UserSeq(userSeq);
        List<MyPageCatsResponseDto> responseDtoList = new ArrayList<>();

        for(Liked liked: LikedList) {
            Cat cat = liked.getCat();
            CatDetail catDetail = catDetailRepository.findFirstByOrderByIdDesc();
            ArrayList<CatDetail> myCatDetailList = catDetailRepository.findAllByUser(userPrincipal.getUser());
            CatDetail myCatDetail = myCatDetailList.get(myCatDetailList.size() -1);
            String lastActivity = String.valueOf(catDetail.getCreatedAt()).replace('T',' ');
            String myActivity = String.valueOf(myCatDetail.getCreatedAt()).replace('T',' ');
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
    public MyPageUserInformationResponseDto getUserInformation(UserPrincipal userPrincipal) {
        Long userSeq = userPrincipal.getUser().getUserSeq();
        User user = userRepository.findByUserSeq(userSeq);
        LocalDateTime start = LocalDateTime.now().minusMonths(2);
        LocalDateTime end = LocalDateTime.now();
        int cntActivity = catDetailRepository.countAllByUserAndModifiedAtBetween(user, start, end);
        return MyPageUserInformationResponseDto.builder()
                .nickname(user.getNickname())
                .username(user.getUsername())
                .profileImageUrl(user.getProfileImageUrl())
                .userLevel(user.getUserLevel())
                .cntActivity(cntActivity)
                .userLocationList(user.getUserLocationList())
                .build();
    }

    @Transactional
    public void putUserInformation(UserPrincipal userPrincipal, UserInformationRequestDto requestDto) {
        User user = userPrincipal.getUser();
        user.update(requestDto);

        //user_Location 데이터들을 다 없애주고 다시 등록하게 해줘야한다.
        userLocationRepository.deleteAllByUser_UserSeq(user.getUserSeq());

        List<UserLocation> userLocationList = convertLocation(user, requestDto.getLocation());
        saveUserLocationList(userLocationList);
        user.addUserLocationList(userLocationList);
        userRepository.save(user);
    }

    public List<UserLocation> convertLocation(User user, List<String> locationStringList) {
        List<UserLocation> userLocationList = new ArrayList<>();
        for (String location : locationStringList) {
            userLocationList.add(new UserLocation(user, location));
        }
        return userLocationList;
    }

    public void saveUserLocationList(List<UserLocation> userLocationList){
        for(UserLocation location : userLocationList){
            userLocationRepository.save(location);
        }
    }


/*
    private List<String> convertToStringList(List<UserLocation> userLocationList) {
        List<String> userLocationStringList = new ArrayList<>();
        for(UserLocation location : userLocationList) {
            userLocationStringList.add(location.getLocation());
        }
        return userLocationStringList;
    }*/

    public List<CalendarResponseDto> myAllActivities(UserPrincipal userPrincipal, int year, int month) {
        User user = userPrincipal.getUser();
        LocalDate startDate = LocalDate.of(year,month,1);
        LocalDate endDate = LocalDate.of(year,month,startDate.lengthOfMonth());
        List<CatCalender> catCalenders = catCalenderRepository.findALLByLocalDateBetweenAndUser(startDate, endDate, user);
        List<CalendarResponseDto> calendarResponseDtos = new ArrayList<>();
        for(LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)){
            CalendarResponseDto calendarResponseDto = new CalendarResponseDto();
            calendarResponseDto.setLocalDate(date);
            for (CatCalender catCalender : catCalenders) {
                if(catCalender.getLocalDate().equals(date)){
                    calendarResponseDto.update(catCalender);
                }
            }
            calendarResponseDtos.add(calendarResponseDto);
        }
        return calendarResponseDtos;
    }

    public List<MyPageCalendarResponseDto> myActivity(UserPrincipal userPrincipal, int year, int month, int day) {
        User user = userPrincipal.getUser();
        LocalDate date = LocalDate.of(year, month, day);
        List<CatCalender> catCalenders = catCalenderRepository.findByLocalDateAndUser(date, user);
        List<MyPageCalendarResponseDto> myPageCalendarResponseDtos = new ArrayList<>();
        for( CatCalender catCalender: catCalenders) {
            myPageCalendarResponseDtos.add(MyPageCalendarResponseDto.builder()
                    .food(catCalender.isFood())
                    .snack(catCalender.isSnack())
                    .water(catCalender.isWater())
                    .catName(catCalender.getCat().getCatName())
                    .catId(catCalender.getCat().getId())
                    .build());
        }
        return myPageCalendarResponseDtos;
    }

}
