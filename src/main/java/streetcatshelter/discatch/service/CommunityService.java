package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.Comment;
import streetcatshelter.discatch.domain.Community;
import streetcatshelter.discatch.domain.CommunityImage;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.dto.requestDto.CommentRequestDto;
import streetcatshelter.discatch.dto.requestDto.CommunityRequestDto;
import streetcatshelter.discatch.dto.responseDto.CommentResponseDto;
import streetcatshelter.discatch.dto.responseDto.CommunityDetailResponseDto;
import streetcatshelter.discatch.dto.responseDto.CommunityResponseDto;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.repository.CommentRepository;
import streetcatshelter.discatch.repository.CommunityImageRepository;
import streetcatshelter.discatch.repository.CommunityLikeitRepository;
import streetcatshelter.discatch.repository.CommunityRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityImageRepository communityImageRepository;
    private final CommentRepository commentRepository;
    private final CommunityLikeitRepository communityLikeitRepository;

    public ArrayList<CommunityResponseDto> getCommunityByCategory(int page, int size, String category, String location,UserPrincipal userPrincipal) {
        //1페이지 문제 해결완료
        User user = userPrincipal.getUser();
        Pageable pageable = PageRequest.of(page -1, size);
        if(category.equals("고양이 정보글"))  {
            Page<Community> communities = communityRepository.findAllByCategory(pageable, category);
            ArrayList<CommunityResponseDto> responseDtoList = new ArrayList<>();
            for (Community community : communities) {
                boolean isLiked = false;
                if (user != null) {
                    isLiked = communityLikeitRepository.existsByCommunityAndUser(community, user);
                }
                String nickname = community.getUser().getNickname();
                String profileImageUrl = community.getUser().getProfileImageUrl();
                String username = community.getUser().getUsername();
                String title = community.getTitle();
                LocalDateTime createdAt = community.getCreatedAt();
                int cntComment = community.getCntComment();
                int cntLikeit = community.getCntLikeit();
                int cntView = community.getCntView();
                Long communityId = community.getId();

                CommunityResponseDto responseDto = new CommunityResponseDto(title, isLiked, nickname, createdAt, cntComment, cntLikeit, cntView, profileImageUrl, communityId, username);
                responseDtoList.add(responseDto);
            }
            return responseDtoList;
        } else {
            Page<Community> communities = communityRepository.findAllByCategoryAndLocation(pageable, category, location);
            //마지막 페이지 여부
            //Boolean isLast = communities.isLast();
            ArrayList<CommunityResponseDto> responseDtoList = new ArrayList<>();
            for (Community community : communities) {
                boolean isLiked = false;
                String nickname = community.getUser().getNickname();
                String profileImageUrl = community.getUser().getProfileImageUrl();
                String username = community.getUser().getUsername();
                if (user != null) {
                    isLiked = communityLikeitRepository.existsByCommunityAndUser(community, user);
                }
                String title = community.getTitle();
                LocalDateTime createdAt = community.getCreatedAt();
                int cntComment = community.getCntComment();
                int cntLikeit = community.getCntLikeit();
                int cntView = community.getCntView();
                Long communityId = community.getId();

                CommunityResponseDto responseDto = new CommunityResponseDto(title, isLiked, nickname, createdAt, cntComment, cntLikeit, cntView, profileImageUrl, communityId, username);
                responseDtoList.add(responseDto);
            }
            return responseDtoList;
        }
    }

    @Transactional
    public CommunityDetailResponseDto getCommunityById(Long communityId, UserPrincipal userPrincipal) {
        Community community = communityRepository.findById(communityId).orElseThrow(()-> new IllegalArgumentException("communityId가 존재하지 않습니다."));
        User user = userPrincipal.getUser();
        int cntView = community.getCntView();
        cntView += 1;
        community.updateCntView(cntView);
        Boolean isLiked = false;
        if(user!=null) {
            isLiked = communityLikeitRepository.existsByCommunityAndUser(community, user);
        }
        List<Comment> commentList = community.getCommentList();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for(Comment comment : commentList) {
            commentResponseDtos.add(CommentResponseDto.builder()
                    .username(comment.getUser().getUsername())
                    .nickname(comment.getUser().getNickname())
                    .contents(comment.getContents())
                    .commentId(comment.getId())
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build());
        }

        return CommunityDetailResponseDto.builder()
                .category(community.getCategory())
                .profileImageUrl(community.getUser().getProfileImageUrl())
                .cntComment(community.getCntComment())
                .communityId(community.getId())
                .cntLikeit(community.getCntLikeit())
                .cntView(community.getCntView())
                .commentList(commentResponseDtos)
                .communityImageList(community.getCommunityImageList())
                .contents(community.getContents())
                .isLiked(isLiked)
                .location(community.getLocation())
                .title(community.getTitle())
                .username(community.getUsername())
                .nickname(community.getUser().getNickname())
                .createdAt(community.getCreatedAt())
                .modifiedAt(community.getModifiedAt())
                .build();
    }

    public void createCommunity(CommunityRequestDto requestDto, UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        Community community = new Community(requestDto, user);
        communityRepository.save(community);

        List<CommunityImage> communityImageList = convertImage(community, requestDto.getImage());
        saveCommunityImageList(communityImageList);
        community.addCommunityImageList(communityImageList);
    }

    @Transactional
    public void createComment(Long communityId, CommentRequestDto requestDto, UserPrincipal userPrincipal) {
        Community community = getCommunity(communityId);
        User user = userPrincipal.getUser();
        Comment comment = new Comment(community, requestDto, user);
        commentRepository.save(comment);
        int cntComment = commentRepository.countAllByCommunityId(communityId);
        community.updateCntComment(cntComment);
    }

    @Transactional
    public void updateComment(Long commentId, CommentRequestDto requestDto, UserPrincipal userPrincipal) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
                );
        User user = userPrincipal.getUser();
        if(user.getUserSeq().equals(comment.getUser().getUserSeq())) {
            comment.update(requestDto);
        } else {
            throw new IllegalArgumentException("유저 정보가 일치하지 않습니다");
        }
    }

    @Transactional
    public void deleteComment(Long commentId, UserPrincipal userPrincipal) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
                );
        User user = userPrincipal.getUser();
        if(user.getUserSeq().equals(comment.getUser().getUserSeq())) {
            commentRepository.deleteById(commentId);
        } else {
            throw new IllegalArgumentException("유저 정보가 일치하지 않습니다");
        }
        Long communityId = comment.getCommunity().getId();
        Community community = getCommunity(communityId);
        int cntComment = commentRepository.countAllByCommunityId(communityId);
        community.updateCntComment(cntComment);
    }

    //update 커뮤니티
    //나중에 코멘트 갯수 세는거는 AOP 기능으로 따로 뺴서 만들어야할듯 코드중복;
    @Transactional
    public Community update(Long communityId, CommunityRequestDto requestDto, UserPrincipal userPrincipal) {
        Community community = getCommunity(communityId);
        User user = userPrincipal.getUser();
        community.update(requestDto, user);

        //community_image 데이터들을 다 없애주고 다시 등록하게 해줘야한다.
        communityImageRepository.deleteAllByCommunityId(communityId);

        List<CommunityImage> communityImageList = convertImage(community, requestDto.getImage());
        saveCommunityImageList(communityImageList);
        community.addCommunityImageList(communityImageList);
        return community;
    }

    public void delete(Long communityId) {
        communityRepository.deleteById(communityId);
    }

    public List<CommunityImage> convertImage(Community community, List<String> imageStringList) {
        List<CommunityImage> communityImageList = new ArrayList<>();
        for (String image : imageStringList) {
            communityImageList.add(new CommunityImage(community, image));
        }
        return communityImageList;
    }

    public void saveCommunityImageList(List<CommunityImage> communityImageList){
        for(CommunityImage image : communityImageList){
            communityImageRepository.save(image);
        }
    }

    public Community getCommunity(Long communityId) {
        return communityRepository.findById(communityId).orElseThrow(()-> new IllegalArgumentException("communityId가 존재하지 않습니다."));
    }
}
