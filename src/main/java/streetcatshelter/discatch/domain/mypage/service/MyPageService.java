package streetcatshelter.discatch.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.Liked;
import streetcatshelter.discatch.domain.cat.domain.Cat;
import streetcatshelter.discatch.domain.cat.domain.CatCalender;
import streetcatshelter.discatch.domain.cat.domain.CatDetail;
import streetcatshelter.discatch.domain.cat.dto.responsedto.ResponseDto;
import streetcatshelter.discatch.domain.cat.repository.CatCalenderRepository;
import streetcatshelter.discatch.domain.cat.repository.CatDetailRepository;
import streetcatshelter.discatch.domain.cat.repository.CatRepository;
import streetcatshelter.discatch.domain.mypage.domain.Notice;
import streetcatshelter.discatch.domain.mypage.dto.*;
import streetcatshelter.discatch.domain.mypage.repository.NoticeRepository;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.domain.user.domain.UserLocation;
import streetcatshelter.discatch.domain.user.dto.UserInformationRequestDto;
import streetcatshelter.discatch.domain.user.repository.UserLocationRepository;
import streetcatshelter.discatch.domain.user.repository.UserRepository;
import streetcatshelter.discatch.repository.CommentRepository;
import streetcatshelter.discatch.repository.LikedRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static streetcatshelter.discatch.service.profileImageUrl.getProfileImageUrl;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final CatDetailRepository catDetailRepository;
    private final CatRepository catRepository;
    private final LikedRepository likedRepository;
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CatCalenderRepository catCalenderRepository;
    private final UserLocationRepository userLocationRepository;

    public ResponseDto findAllCats(UserPrincipal userPrincipal, int page, int size) {
        Long userSeq = userPrincipal.getUser().getUserSeq();
        Pageable pageable = PageRequest.of(page -1, size);

        Page<Liked> LikedList = likedRepository.findAllByUser_UserSeq(userSeq, pageable);
        Boolean isLast = LikedList.isLast();
        List<MyPageCatsResponseDto> responseDtoList = new ArrayList<>();

        for(Liked liked: LikedList) {
            Cat cat = liked.getCat();
            CatDetail catDetail = catDetailRepository.findFirstByOrderByIdDesc();
            String lastActivity;

            if(catDetail != null) {
                lastActivity = String.valueOf(catDetail.getCreatedAt()).replace('T',' ');
            } else {
                lastActivity = String.valueOf(cat.getCreatedAt()).replace('T',' ');
            }

            ArrayList<CatDetail> myCatDetailList = catDetailRepository.findAllByUser(userPrincipal.getUser());
            String myActivity;
            if(myCatDetailList.size() == 0) {
                myActivity = null;
            } else {
                CatDetail myCatDetail = myCatDetailList.get(myCatDetailList.size() -1);
                myActivity = String.valueOf(myCatDetail.getCreatedAt()).replace('T',' ');
            }

            String catName = cat.getCatName();
            String catImage = cat.getCatImage();
            Long catId = cat.getId();
            int cntComment = commentRepository.countAllByUser_UserSeqAndCatId(userSeq, catId);
            int cntCatDetail = catDetailRepository.countAllByUser_UserSeqAndCatId(userSeq, catId);
            String location = cat.getLocation().split(" ")[(int) (Arrays.stream(cat.getLocation().split(" ")).count()-1)];
            double latitude = cat.getLatitude();
            double longitude = cat.getLongitude();
            MyPageCatsResponseDto myPageCatsResponseDto = new MyPageCatsResponseDto(lastActivity, myActivity, catName, catImage, catId, cntComment, cntCatDetail, location, latitude, longitude);
            responseDtoList.add(myPageCatsResponseDto);

        }

        return ResponseDto.builder()
                .responses(responseDtoList)
                .isLast(isLast)
                .build();
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

        List<String> location = new ArrayList<>();
        List<UserLocation> orgLocationList = user.getUserLocationList();
        for(UserLocation userLocation : orgLocationList) {
            location.add(userLocation.getLocation());
        }
        /*String profileImageUrl = null;
        if(user.getProfileUrl() == null) {
            profileImageUrl = user.getProfileImageUrl();
        } else {
            profileImageUrl = user.getProfileUrl();
        }*/
        String profileImageUrl = getProfileImageUrl(user);
        int cntActivity = catDetailRepository.countAllByUserAndModifiedAtBetween(user, start, end);
        return MyPageUserInformationResponseDto.builder()
                .nickname(user.getNickname())
                .username(user.getUsername())
                .profileImageUrl(profileImageUrl)
                .userLevel(user.getUserLevel())
                .cntActivity(cntActivity)
                .LocationList(location)
                .score(user.getScore())
                .scoreLeft(user.getScoreLeft())
                .nextLevel(String.valueOf(user.getUserLevel().nextLevel()))
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

    public List<MyPageAllCalendarResponseDto> myAllActivities(UserPrincipal userPrincipal, int year, int month) {
        User user = userPrincipal.getUser();
        LocalDate startDate = LocalDate.of(year,month,1);
        LocalDate endDate = LocalDate.of(year,month,startDate.lengthOfMonth());

        List<CatCalender> catCalenders = catCalenderRepository.findALLByLocalDateBetweenAndUser(startDate, endDate, user);
        List<MyPageAllCalendarResponseDto> calendarResponseDtos = new ArrayList<>();
        for (CatCalender catCalender : catCalenders) {
            if(calendarResponseDtos.size() == 0 || !calendarResponseDtos.get(calendarResponseDtos.size()-1).getDate().equals(catCalender.getLocalDate())) {
                calendarResponseDtos.add(MyPageAllCalendarResponseDto.builder()
                        //.date(String.valueOf(catCalender.getLocalDate()))
                        .date(catCalender.getLocalDate())
                        .food(catCalender.isFood())
                        .snack(catCalender.isSnack())
                        .water(catCalender.isWater())
                        .build());
            }
        }
        return calendarResponseDtos;
    }

    public List<MyPageCalendarResponseDto> myActivity(UserPrincipal userPrincipal, int year, int month, int day) {
        User user = userPrincipal.getUser();
        LocalDate date = LocalDate.of(year, month, day);
        List<CatCalender> catCalenders = catCalenderRepository.findByLocalDateAndUser(date, user);
        List<MyPageCalendarResponseDto> myPageCalendarResponseDtos = new ArrayList<>();
        for(CatCalender catCalender: catCalenders) {
            myPageCalendarResponseDtos.add(MyPageCalendarResponseDto.builder()
                    .food(catCalender.isFood())
                    .snack(catCalender.isSnack())
                    .water(catCalender.isWater())
                    .catName(catCalender.getCat().getCatName())
                    .catId(catCalender.getCat().getId())
                    .catImage(catCalender.getCat().getCatImage())
                    .location(catCalender.getCat().getLocation().split(" ")[(int) (Arrays.stream(catCalender.getCat().getLocation().split(" ")).count()-1)])
                    .build());
        }
        return myPageCalendarResponseDtos;
    }

}
