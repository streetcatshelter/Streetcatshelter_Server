package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.Community;
import streetcatshelter.discatch.domain.CommunityLikeit;
import streetcatshelter.discatch.domain.User;
import streetcatshelter.discatch.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.repository.CommunityLikeitRepository;
import streetcatshelter.discatch.repository.CommunityRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityLikeitService {

    private final CommunityLikeitRepository communityLikeitRepository;
    private final CommunityRepository communityRepository;

    @Transactional
    public String CreateAndDeleteCommunityLikeit(Long communityId, UserPrincipal userPrincipal) {
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new IllegalArgumentException("해당 커뮤니티가 없습니다"));
        User user = userPrincipal.getUser();

        Boolean isExist = communityLikeitRepository.existsByCommunityAndUser(community, user);

        if(isExist) {
            communityLikeitRepository.deleteByCommunityAndUser(community, user);
            community.updateCntLikeit(-1);
            return "커뮤니티 좋아요 취소가 완료되었습니다";
        } else {
            CommunityLikeit communityLikeit = new CommunityLikeit(community, user);
            communityLikeitRepository.save(communityLikeit);
            community.updateCntLikeit(+1);
            return "커뮤니티 좋아요 추가가 완료되었습니다";
        }
    }
}