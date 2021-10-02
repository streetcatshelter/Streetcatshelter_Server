package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import streetcatshelter.discatch.domain.*;
import streetcatshelter.discatch.dto.CatRequestDto;
import streetcatshelter.discatch.dto.CommentRequestDto;
import streetcatshelter.discatch.dto.response.CatDetailResponseDto;
import streetcatshelter.discatch.dto.response.CatDiaryResponseDto;
import streetcatshelter.discatch.dto.response.CatGalleryResponseDto;
import streetcatshelter.discatch.repository.*;

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
    private final CatImageRepository catImageRepository;

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



    @Transactional
    public CatDetailResponseDto getCatDetail(Long catDetailId) {
        CatDetail catDetail = catDetailRepository.findById(catDetailId).orElseThrow(
                () -> new NullPointerException("No Such Data")
        );
        catDetail.updateView();
        return CatDetailResponseDto.builder()
                .food(catDetail.isFood())
                .snack(catDetail.isSnack())
                .water(catDetail.isWater())
                .commentCnt(catDetail.getCommentCnt())
                .diary(catDetail.getDiary())
                .likeCnt(catDetail.getLikeCnt())
                .viewCnt(catDetail.getViewCnt())
                .build();
    }


    public List<CatGalleryResponseDto> getCatPhotos(int page, int size, Long catId) {
        page -= 1;
        Pageable pageable = PageRequest.of(page, size);
        List<CatImage> allByCatId = catImageRepository.findAllByCatId(pageable, catId);
        List<CatGalleryResponseDto> catGalleryResponseDtos = new ArrayList<>();
        for (CatImage catImage : allByCatId) {
            catGalleryResponseDtos.add(CatGalleryResponseDto.builder()
                    .CatDetailId(catImage.getCatDetail().getId())
                    .CatImages(catImage.getImage())
                    .build());
        }
        return catGalleryResponseDtos;
    }

    @Transactional
    public void createDetailComment(Long catDetailId,CommentRequestDto commentRequestDto, User user) {
        CatDetail catDetail = catDetailRepository.findById(catDetailId).orElseThrow(
                () -> new NullPointerException("No Such Data")
        );
        catDetail.updateCommentCnt();
        Comment comment = new Comment(catDetail,commentRequestDto,user);
        commentRepository.save(comment);
    }

    public void createComment(Long catId, CommentRequestDto commentRequestDto, User user) {
        Cat cat = catRepository.findById(catId).orElseThrow(
                ()-> new NullPointerException("NO SUCH DATA")
        );

        Comment comment = new Comment(cat,commentRequestDto,user);
        commentRepository.save(comment);

    }

    public Page<Comment> getCatCommentByCatDetail(Long catDetailId, int page, int size) {
        page -= 1;
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findAllByCatDetailId(pageable,catDetailId);
    }

    public List<CatDiaryResponseDto> getCatDiaryByCat(Long catId, int page, int size) {
        page -= 1;
        Pageable pageable = PageRequest.of(page, size);
        Page<CatDetail> allByCatId = catDetailRepository.findAllByCatId(pageable, catId);
        List<CatDiaryResponseDto> catDiaryResponseDtos = new ArrayList<>();
        for (CatDetail catDetail : allByCatId) {
            catDiaryResponseDtos.add(CatDiaryResponseDto.builder()
                    .diary(catDetail.getDiary())
                    .catDetailId(catDetail.getId())
                    .commentCnt(catDetail.getCommentCnt())
                    .likeCnt(catDetail.getLikeCnt())
                    .viewCnt(catDetail.getViewCnt())
                    .user(catDetail.getUser())
                    .build());
        }return catDiaryResponseDtos;
    }
}
