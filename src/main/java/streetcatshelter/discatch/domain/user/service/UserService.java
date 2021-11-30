package streetcatshelter.discatch.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.domain.Liked;
import streetcatshelter.discatch.domain.cat.repository.CatDetailRepository;
import streetcatshelter.discatch.domain.cat.repository.CatRepository;
import streetcatshelter.discatch.domain.chat.dto.LoginResponseDto;
import streetcatshelter.discatch.domain.community.repository.CommunityRepository;
import streetcatshelter.discatch.domain.config.properties.AppProperties;
import streetcatshelter.discatch.domain.oauth.entity.ProviderType;
import streetcatshelter.discatch.domain.oauth.entity.RoleType;
import streetcatshelter.discatch.domain.oauth.entity.UserPrincipal;
import streetcatshelter.discatch.domain.oauth.social.*;
import streetcatshelter.discatch.domain.oauth.token.JwtTokenProvider;
import streetcatshelter.discatch.domain.user.domain.User;
import streetcatshelter.discatch.domain.user.domain.UserLevel;
import streetcatshelter.discatch.domain.user.domain.UserLocation;
import streetcatshelter.discatch.domain.user.dto.UserInfoResponseDto;
import streetcatshelter.discatch.domain.user.repository.UserRepository;
import streetcatshelter.discatch.repository.CommentRepository;
import streetcatshelter.discatch.repository.LikedRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;
    private final KakaoOAuth2 kakaoOAuth2;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final NaverOAuth2 naverOAuth2;
    private final GoogleOAuth2 googleOAuth2;
    private final LikedRepository likedRepository;
    private final CatRepository catRepository;
    private final CommunityRepository communityRepository;
    private final CatDetailRepository catDetailRepository;
    private final CommentRepository commentRepository;


    public LoginResponseDto kakaoLogin(String code) {
        KakaoUserInfo kakaoUserInfo = kakaoOAuth2.getUserInfo(code);
        String kakaoId = kakaoUserInfo.getId().toString();
        // 패스워드 = 카카오 Id + ADMIN TOKEN
        String password = kakaoId + appProperties.getAuth().getTokenSecret();

        // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByUserId(kakaoId);

        // 카카오 정보로 회원가입
//        User user = new User(
//                userInfo.getId(),
//                userInfo.getName(),
//                userInfo.getEmail(),
//                "Y",
//                userInfo.getImageUrl(),
//                providerType,
//                RoleType.USER
//        );

        if (kakaoUser == null) {
            // 패스워드 인코딩
            String encodedPassword = passwordEncoder.encode(password);

            kakaoUser = new User(
                    kakaoUserInfo.getId().toString(),
                    kakaoUserInfo.getProperties().getNickname(),
                    kakaoUserInfo.getKakao_account().getEmail(),
                    "y",
                    kakaoUserInfo.getKakao_account().getProfile().getProfile_image_url(),
                    ProviderType.KAKAO,
                    RoleType.USER,
                    UserLevel.아깽이);

            userRepository.save(kakaoUser);
        }

        return new LoginResponseDto(kakaoUser,jwtTokenProvider.createToken(kakaoId));
    }



    public LoginResponseDto naverLogin(String code) {


        NaverUserInfo naverUserInfo = naverOAuth2.getUserInfo(code);

        String id = naverUserInfo.getId();

        User naverUser= userRepository.findByUserId(id);

        if (naverUser == null) {
            // 패스워드 인코딩

            naverUser = new User(
                    naverUserInfo.getId(),
                    naverUserInfo.getName(),
                    naverUserInfo.getNickname(),
                    naverUserInfo.getEmail(),
                    "y",
                    naverUserInfo.getProfile_image(),
                    ProviderType.NAVER,
                    RoleType.USER,
                    UserLevel.아깽이);

            userRepository.save(naverUser);
        }


        return new LoginResponseDto(naverUser,jwtTokenProvider.createToken(id));
    }


    public LoginResponseDto googleLogin(String code) {
        GoogleUserInfo googleUserInfo = googleOAuth2.getUserInfo(code);

        String id = googleUserInfo.getId();

        User googleUser = userRepository.findByUserId(id);

        if(googleUser == null){
            User user = User.googleUserCreate(googleUserInfo);
            userRepository.save(user);
        }

        assert googleUser != null;
        return new LoginResponseDto(googleUser,jwtTokenProvider.createToken(id));

    }

    public static void userChecker(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            throw new IllegalArgumentException("유저정보가 없습니다. 토큰 잘못됨");
        }
    }


    public UserInfoResponseDto getUserInfo(String userRandomId) {
        User user = userRepository.findByUserRandomId(userRandomId);
        Long userSeq = user.getUserSeq();
        List<Liked> LikedList = likedRepository.findAllByUser_UserSeq(userSeq);
        List<String> catImages = new ArrayList<>();
        for(Liked liked: LikedList) {
            String catImage = liked.getCat().getCatImage();
            catImages.add(catImage);
        }

        List<UserLocation> userLocationList = user.getUserLocationList();
        List<String> locationList = new ArrayList<>();
        for(UserLocation userLocation : userLocationList) {
            locationList.add(userLocation.getLocation());
        }
        int catDetailNum = catDetailRepository.countAllByUser_UserSeq(userSeq);
        int communityNum = communityRepository.countAllByUser_UserSeq(userSeq);
        int catNum = catRepository.countAllByUser_UserSeq(userSeq);
        int commentNum = commentRepository.countAllByUser_UserSeq(userSeq);
        int likedNum = likedRepository.countAllByUser_UserSeq(userSeq);



        return UserInfoResponseDto.builder()
                .cat(catImages)
                .location(locationList)
                .userLevel(user.getUserLevel())
                .nickname(user.getNickname())
                .score(user.getScore())
                .catNum(catNum)
                .postNum(catDetailNum + communityNum)
                .commentNum(commentNum)
                .likedNum(likedNum)
                .userRandomId(user.getUserRandomId())
                .build();
    }
}
