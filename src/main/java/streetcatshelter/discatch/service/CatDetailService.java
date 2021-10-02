package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import streetcatshelter.discatch.domain.Cat;
import streetcatshelter.discatch.domain.CatDetail;
import streetcatshelter.discatch.domain.CatImage;
import streetcatshelter.discatch.domain.User;
import streetcatshelter.discatch.dto.CatDetailRequestDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.repository.CatDetailRepository;
import streetcatshelter.discatch.repository.CatImageRepository;
import streetcatshelter.discatch.repository.CatRepository;
import streetcatshelter.discatch.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatDetailService {

    private final CatDetailRepository catDetailRepository;
    private final CatRepository catRepository;
    private final CommentRepository commentRepository;
    private final CatImageRepository catImageRepository;

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
}
