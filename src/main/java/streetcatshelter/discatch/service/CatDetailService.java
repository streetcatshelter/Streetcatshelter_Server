package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import streetcatshelter.discatch.domain.Cat;
import streetcatshelter.discatch.domain.CatDetail;
import streetcatshelter.discatch.domain.Liked;
import streetcatshelter.discatch.domain.User;
import streetcatshelter.discatch.dto.requestDto.CatDetailRequestDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.repository.*;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CatDetailService {

    private final CatDetailRepository catDetailRepository;
    private final CatRepository catRepository;
    private final CommentRepository commentRepository;
    private final CatImageRepository catImageRepository;
    private final LikedRepository likedRepository;

    @Transactional
    public void createCatDetail(CatDetailRequestDto requestDto, Long catId, UserPrincipal userPrincipal) {

        Cat cat = getCat(catId);
        User user = userPrincipal.getUser();
        CatDetail catDetail = new CatDetail(requestDto,cat,user);


        catDetailRepository.save(catDetail);

    }

    private Cat getCat(Long catId) {
        return catRepository.findById(catId).orElseThrow(()-> new IllegalArgumentException("catId가 존재하지 않습니다."));
    }

    @Transactional
    public Map<String,Long> addlike(Long catDetailId,User user) {
        CatDetail catDetail = catDetailRepository.findById(catDetailId).orElseThrow(
                () -> new NullPointerException("No Such Data")
        );
        Liked byCatDetailIdAndUser_userSeq = likedRepository.findByCatDetailIdAndUser_UserSeq(catDetailId, user.getUserSeq());

        if(byCatDetailIdAndUser_userSeq == null ){
            catDetail.getLikeds().add(new Liked(catDetail,user));
            Map<String,Long> map = new HashMap<>();
            map.put("UpdatedLikeCnt",catDetail.updateLikeCnt(1L));
            return map;
        }else{
            likedRepository.delete(byCatDetailIdAndUser_userSeq);
            Map<String,Long> map = new HashMap<>();
            map.put("UpdatedLikeCnt",catDetail.updateLikeCnt(-1L));
            return map;
        }
    }

    public void deleteCatDetail(Long catDetailId, User user) {
        CatDetail catDetail = catDetailRepository.findById(catDetailId).orElseThrow(
                () -> new NullPointerException("NO SUCH DATA")
        );
        if(catDetail.getUser().equals(user)){
            catDetailRepository.delete(catDetail);
        }else {
            throw new IllegalArgumentException("권한이 없습니다.!");
        }

    }
}
