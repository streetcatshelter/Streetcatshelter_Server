package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.Cat;
import streetcatshelter.discatch.domain.CatDetail;
import streetcatshelter.discatch.domain.CatTag;
import streetcatshelter.discatch.domain.Comment;
import streetcatshelter.discatch.dto.CatRequestDto;
import streetcatshelter.discatch.repository.CatDetailRepository;
import streetcatshelter.discatch.repository.CatRepository;
import streetcatshelter.discatch.repository.CatTagRepository;
import streetcatshelter.discatch.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CatService {
    private final CatRepository catRepository;
    private final CatTagRepository catTagRepository;
    private final CatDetailRepository catDetailRepository;
    private final CommentRepository commentRepository;

    public Page<Cat> getCatByLocation(int page, int size, String location) {
        page -= 1;
        Pageable pageable = PageRequest.of(page, size);
        return catRepository.findAllByLocation(pageable,location);
    }

    public void createCat(CatRequestDto requestDto) {
        Cat cat = new Cat(requestDto);
        catRepository.save(cat);

        List<CatTag> catTagList = convertTag(cat, requestDto.getCatTag());
        saveCatTagList(catTagList);
        cat.addCatTagList(catTagList);

    }

    public List<CatTag> convertTag(Cat cat, List<String> catTagStringList) {
        List<CatTag> catTagList = new ArrayList<>();
        for (String tag : catTagStringList) {
            catTagList.add(new CatTag(cat, tag));
        }
        return catTagList;
    }

    public void saveCatTagList(List<CatTag> catTagList){
        for(CatTag tag : catTagList){
            catTagRepository.save(tag);
        }
    }

    public List<CatDetail> getCatDetailByCat(Long catId) {
        return catDetailRepository.findAllByCat(catId);
    }

    public List<Comment> getCatCommentByCat(Long catId) {
        return commentRepository.findAllByCatId(catId);
    }

    public Optional<CatDetail> getCatDetail(Long catDetailId) {
        return catDetailRepository.findById(catDetailId);
    }
}
