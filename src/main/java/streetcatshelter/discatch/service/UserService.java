package streetcatshelter.discatch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import streetcatshelter.discatch.config.properties.AppProperties;
import streetcatshelter.discatch.domain.User;
import streetcatshelter.discatch.oauth.entity.ProviderType;
import streetcatshelter.discatch.oauth.entity.RoleType;
import streetcatshelter.discatch.oauth.social.KakaoOAuth2;
import streetcatshelter.discatch.oauth.social.KakaoUserInfo;
import streetcatshelter.discatch.oauth.token.JwtTokenProvider;
import streetcatshelter.discatch.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;
    private final KakaoOAuth2 kakaoOAuth2;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String kakaoLogin(String code) {
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
                    RoleType.USER);

            userRepository.save(kakaoUser);
        }

        return jwtTokenProvider.createToken(kakaoId);
    }

    public String naverLogin(String code) {
        return null;
    }
}
