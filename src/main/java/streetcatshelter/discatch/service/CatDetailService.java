package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.Cat;
import streetcatshelter.discatch.domain.CatDetail;
import streetcatshelter.discatch.dto.CatDetailRequestDto;
import streetcatshelter.discatch.repository.CatDetailRepository;
import streetcatshelter.discatch.repository.CatRepository;
import streetcatshelter.discatch.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CatDetailService {

    private final CatDetailRepository catDetailRepository;
    private final CatRepository catRepository;
    private final CommentRepository commentRepository;

    public void createCatDetail(CatDetailRequestDto requestDto, Long catId, UserPrincipal userPrincipal) {
        Cat cat = getCat(catId);
        User user =
        CatDetail catDetail = new CatDetail(requestDto, cat);
        catDetailRepository.save(catDetail);
    }

    private Cat getCat(Long catId) {
        return catRepository.findById(catId).orElseThrow(()-> new IllegalArgumentException("communityId가 존재하지 않습니다."));
    }
}
