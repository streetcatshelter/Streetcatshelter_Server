package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.*;
import streetcatshelter.discatch.dto.requestDto.UserInformationRequestDto;
import streetcatshelter.discatch.dto.responseDto.MyPageCatsResponseDto;
import streetcatshelter.discatch.dto.responseDto.MyPageNoticeResponseDto;
import streetcatshelter.discatch.dto.responseDto.MyPageUserInformationResponseDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.repository.CatDetailRepository;
import streetcatshelter.discatch.repository.LikedRepository;
import streetcatshelter.discatch.repository.NoticeRepository;
import streetcatshelter.discatch.repository.UserRepository;

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
            int cntComment = cat.getCntComment();
            int cntCatDetail = catDetailRepository.findAllByCatId(cat.getId()).size();
            MyPageCatsResponseDto myPageCatsResponseDto = new MyPageCatsResponseDto(lastActivity, myActivity, catName, catImage, catId, cntComment, cntCatDetail);
            responseDtoList.add(myPageCatsResponseDto);
        }

        return responseDtoList;
    }


    public List<MyPageNoticeResponseDto> getAllNotices() {
        List<Notice> noticeList = noticeRepository.findAllByOrderByIdDesc();
        List<MyPageNoticeResponseDto> responseDtoList = new ArrayList<>();
        for(Notice notice : noticeList) {
            MyPageNoticeResponseDto myPageNoticeResponseDto = new MyPageNoticeResponseDto(notice);
            responseDtoList.add(myPageNoticeResponseDto);
        }
        return responseDtoList;
    }

    public MyPageNoticeResponseDto getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 notice는 존재하지 않습니다"));
        return new MyPageNoticeResponseDto(notice);
    }

    @Transactional
    public MyPageUserInformationResponseDto putUserInformation(UserPrincipal userPrincipal, UserInformationRequestDto requestDto) {
        User user = userPrincipal.getUser();
        if(requestDto.getNickname() == null) {
            return new MyPageUserInformationResponseDto("닉네임을 입력해주세요");
        } else if (requestDto.getLocation() == null && requestDto.getLocation2() == null && requestDto.getLocation3()==null) {
            return new MyPageUserInformationResponseDto("최소 1곳 이상의 동네를 입력해주세요");
        } else {
            user.update(requestDto);
            userRepository.save(user);
            return new MyPageUserInformationResponseDto("회원 정보 등록/수정이 완료되었습니다.");
        }
    }


}
