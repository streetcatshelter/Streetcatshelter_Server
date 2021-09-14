package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.Community;
import streetcatshelter.discatch.domain.CommunityImage;
import streetcatshelter.discatch.dto.CommunityRequestDto;
import streetcatshelter.discatch.repository.CommunityImageRepository;
import streetcatshelter.discatch.repository.CommunityRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityImageRepository communityImageRepository;


    public Page<Community> getCommunityByCategory(int page, int size, String category, String location) {
        Pageable pageable = PageRequest.of(page, size);
        return communityRepository.findAllByCategoryAndLocation(pageable, category, location);
    }

    public void createCommunity(CommunityRequestDto requestDto) {
        Community community = new Community(requestDto);
        communityRepository.save(community);

        List<CommunityImage> communityImageList = convertImage(community, requestDto.getImage());
        saveCommunityImageList(communityImageList);
        community.addCommunityImageList(communityImageList);
    }

    @Transactional
    public Community update(Long communityId, CommunityRequestDto requestDto) {
        Community community = getCommunity(communityId);
        community.update(requestDto);

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
