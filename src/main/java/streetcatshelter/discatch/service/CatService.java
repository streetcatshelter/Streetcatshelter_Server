package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import streetcatshelter.discatch.domain.*;
import streetcatshelter.discatch.dto.requestDto.CatRequestDto;
import streetcatshelter.discatch.dto.requestDto.CommentRequestDto;
import streetcatshelter.discatch.dto.responseDto.CatDetailResponseDto;
import streetcatshelter.discatch.dto.responseDto.CatDiaryResponseDto;
import streetcatshelter.discatch.dto.responseDto.CatGalleryResponseDto;
import streetcatshelter.discatch.dto.responseDto.CommentResponseDto;
import streetcatshelter.discatch.repository.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatService {
    private final CatRepository catRepository;
    private final CatTagRepository catTagRepository;
    private final CatDetailRepository catDetailRepository;
    private final CommentRepository commentRepository;
    private final CatImageRepository catImageRepository;
    private final LikedRepository likedRepository;

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
    public CatDetailResponseDto getCatDetail(Long catDetailId,User user) {
        CatDetail catDetail = catDetailRepository.findById(catDetailId).orElseThrow(
                () -> new NullPointerException("No Such Data")
        );
        boolean b = likedRepository.existsByUser_UserSeqAndCatDetailId( user.getUserSeq(),catDetailId);
        catDetail.updateView();
        return CatDetailResponseDto.builder()
                .food(catDetail.isFood())
                .snack(catDetail.isSnack())
                .water(catDetail.isWater())
                .isUserLiked(b)
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

    public List<CommentResponseDto> getCatCommentByCatDetail(Long catDetailId, int page, int size) {
        page -= 1;
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> allByCatDetailId = commentRepository.findAllByCatDetailId(pageable, catDetailId);
        return getCommentResponseDtos(allByCatDetailId);
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
                    .profileImageUrl(catDetail.getUser().getProfileImageUrl())
                    .userId(catDetail.getUser().getUserSeq())
                    .username(catDetail.getUser().getUsername())
                    .build());
        }return catDiaryResponseDtos;
    }

    public List<CommentResponseDto> getCatComment(Long catId, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Comment> allByCatDetailId = commentRepository.findAllByCatId(pageable, catId);
        return getCommentResponseDtos(allByCatDetailId);
    }

    private List<CommentResponseDto> getCommentResponseDtos(Page<Comment> allByCatDetailId) {
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for (Comment comment : allByCatDetailId) {
            commentResponseDtos.add(CommentResponseDto.builder()
                    .commentId(comment.getId())
                    .contents(comment.getContents())
                    .username(comment.getUser().getUsername())
                    .userId(comment.getUser().getUserSeq())
                    .profileImageUrl(comment.getUser().getProfileImageUrl())
                    .build());
        }
        return commentResponseDtos;
    }

}
