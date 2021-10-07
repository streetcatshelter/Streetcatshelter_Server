package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.*;
import streetcatshelter.discatch.dto.requestDto.UserInformationRequestDto;
import streetcatshelter.discatch.dto.responseDto.MyPageCatsResponseDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.repository.CatDetailRepository;
import streetcatshelter.discatch.repository.CatRepository;
import streetcatshelter.discatch.repository.LikedRepository;
import streetcatshelter.discatch.repository.NoticeRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final CatRepository catRepository;
    private final CatDetailRepository catDetailRepository;
    private final LikedRepository likedRepository;
    private final NoticeRepository noticeRepository;

    public ArrayList<MyPageCatsResponseDto> findAllCats(UserPrincipal userPrincipal) {
        Long userSeq = userPrincipal.getUser().getUserSeq();
        List<Liked> LikedList = likedRepository.findAllByUser_UserSeq(userSeq);
        ArrayList<MyPageCatsResponseDto> responseDtoList = new ArrayList<>();

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


    public List<Notice> getAllNotices() {
        List<Notice> noticeList = noticeRepository.findAllByOrderByIdDesc();
        return noticeList;
    }

    public String putUserInformation(UserPrincipal userPrincipal, UserInformationRequestDto requestDto) {
        User user = userPrincipal.getUser();
        if(requestDto.getNickname() == null) {
            return "닉네임을 입력해 주세요";
        } else if (requestDto.getLocation() ==null) {
            return "최소 동네 1곳은 저장해야 합니다";
        } else {
            user.update(requestDto);
            return "회원정보 등록/변경이 완료되었습니다";
        }
    }
}
