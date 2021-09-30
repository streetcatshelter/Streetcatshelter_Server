package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.Comment;
import streetcatshelter.discatch.domain.Community;
import streetcatshelter.discatch.domain.CommunityImage;
import streetcatshelter.discatch.domain.User;
import streetcatshelter.discatch.dto.CommentRequestDto;
import streetcatshelter.discatch.dto.CommunityRequestDto;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.repository.CommentRepository;
import streetcatshelter.discatch.repository.CommunityImageRepository;
import streetcatshelter.discatch.repository.CommunityRepository;
import streetcatshelter.discatch.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityImageRepository communityImageRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    //1페이지 문제 해결완료
    public Page<Community> getCommunityByCategory(int page, int size, String category, String location) {
        page -= 1;
        Pageable pageable = PageRequest.of(page, size);
        return communityRepository.findAllByCategoryAndLocation(pageable, category, location);
    }

    @Transactional
    public Community getCommunityById(Long communityId) {
        Community community = getCommunity(communityId);
        int cntView = community.getCntView();
        cntView += 1;
        community.updateCntView(cntView);
        return communityRepository.findById(communityId).orElseThrow(()-> new IllegalArgumentException("communityId가 존재하지 않습니다."));
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
                        () -> new IllegalArgumentException("해당 대댓글이 존재하지 않습니다.")
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
