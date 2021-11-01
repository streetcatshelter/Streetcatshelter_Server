package streetcatshelter.discatch.domain.cat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import streetcatshelter.discatch.aop.UpdateUserScore;
import streetcatshelter.discatch.domain.Comment;
import streetcatshelter.discatch.domain.Liked;
import streetcatshelter.discatch.domain.cat.domain.Cat;
import streetcatshelter.discatch.domain.cat.domain.CatDetail;
import streetcatshelter.discatch.domain.cat.domain.CatImage;
import streetcatshelter.discatch.domain.cat.domain.CatTag;
import streetcatshelter.discatch.domain.cat.dto.requestdto.CatRequestDto;
import streetcatshelter.discatch.domain.cat.dto.responsedto.CatDetailResponseDto;
import streetcatshelter.discatch.domain.cat.dto.responsedto.CatDiaryResponseDto;
import streetcatshelter.discatch.domain.cat.dto.responsedto.CatGalleryResponseDto;
import streetcatshelter.discatch.domain.cat.dto.responsedto.CatResponseDto;
import streetcatshelter.discatch.domain.cat.repository.*;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.dto.requestDto.CommentRequestDto;
import streetcatshelter.discatch.dto.responseDto.CommentResponseDto;
import streetcatshelter.discatch.repository.CommentRepository;
import streetcatshelter.discatch.repository.LikedRepository;

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
    private final CatCalenderRepository catCalenderRepository;

    public List<CatResponseDto> getCatByLocation(int page, int size, String location, User user) {
        Pageable pageable = PageRequest.of(page -1, size);
        Page<Cat> cats = catRepository.findAllByLocation(pageable,location);
        List<CatResponseDto> responseDtoList = new ArrayList<>();
        List<Liked> likeds = likedRepository.findAllByUser_UserSeq(user.getUserSeq());

        for(Cat cat : cats){
            responseDtoList.add(CatResponseDto.builder()
                                .userLiked(false)
                                .catId(cat.getId())
                                .catName(cat.getCatName())
                                .catImage(cat.getCatImage())
                                .neutering(cat.getNeutering())
                                .catTagList(cat.getCatTagList())
                                .build());
        }

        if(likeds.size()!=0){
            for (CatResponseDto catResponseDto : responseDtoList) {
                for (Liked liked : likeds) {

                    if(catResponseDto.getCatId().equals(liked.getCat().getId())){

                        catResponseDto.setUserLiked(true);

                    }

                }
            }
        }

        return responseDtoList;
    }

    @UpdateUserScore
    public void createCat(CatRequestDto requestDto, User user) {
        Cat cat = new Cat(requestDto, user);
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
    public String addlike(Long catId, User user) {
        Cat cat = catRepository.findById(catId).orElseThrow(
                () -> new NullPointerException("No Such Data")
        );
        Liked byCatIdAndUser_userSeq = likedRepository.findByCatIdAndUser_UserSeq(catId, user.getUserSeq());

        if(byCatIdAndUser_userSeq == null ){
            Liked liked = new Liked(cat,user);
            likedRepository.save(liked);
            return "좋아요 완료";
        }else{
            likedRepository.delete(byCatIdAndUser_userSeq);
            return "좋아요 취소 완료";
        }
    }


    @Transactional
    public CatDetailResponseDto getCatDetail(Long catDetailId, User user) {
        CatDetail catDetail = catDetailRepository.findById(catDetailId).orElseThrow(
                () -> new NullPointerException("No Such Data")
        );
        boolean b = likedRepository.existsByUser_UserSeqAndCatDetailId( user.getUserSeq(),catDetailId);
        catDetail.updateView();
        List<String > catImages = new ArrayList<>();
        for(CatImage catImage : catDetail.getCatImages()){
            catImages.add(catImage.getImage());
        }
        List<String> catTags = new ArrayList<>();
        for(CatTag catTag : catDetail.getCatTags()){
            catTags.add(catTag.getTag());
        }
        return CatDetailResponseDto.builder()
                .catDetailId(catDetail.getId())
                .food(catDetail.isFood())
                .snack(catDetail.isSnack())
                .water(catDetail.isWater())
                .isUserLiked(b)
                .commentCnt(catDetail.getCommentCnt())
                .diary(catDetail.getDiary())
                .likeCnt(catDetail.getLikeCnt())
                .viewCnt(catDetail.getViewCnt())
                .catImages(catImages)
                .catTags(catTags)
                .createdAt(catDetail.getCreatedAt())
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

    @UpdateUserScore
    @Transactional
    public void createDetailComment(Long catDetailId,CommentRequestDto commentRequestDto, User user) {
        CatDetail catDetail = catDetailRepository.findById(catDetailId).orElseThrow(
                () -> new NullPointerException("No Such Data")
        );
        catDetail.updateCommentCnt();
        Comment comment = new Comment(catDetail,commentRequestDto,user);
        commentRepository.save(comment);
    }

    @UpdateUserScore
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
                    .createdAt(catDetail.getCreatedAt())
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
                    .createdAt(comment.getCreatedAt())
                    .build());
        }
        return commentResponseDtos;
    }

    @UpdateUserScore
    public void deleteComment(Long commentId, User user) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("NO SUCH COMMENT")
        );

        if(!comment.getUser().getUserSeq().equals(user.getUserSeq())){
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }


}
