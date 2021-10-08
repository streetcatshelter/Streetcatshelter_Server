package streetcatshelter.discatch.oauth.social;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class KakaoUserInfo {

    public Integer id;
    public String connected_at;
    public Properties properties;
    public Kakao_account kakao_account;


    @Data
    @NoArgsConstructor
    public static class Kakao_account {

        public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;

    }

    @Data
    @NoArgsConstructor
    public static class Profile {

        public String nickname;
        public String thumbnail_image_url;
        public String profile_image_url;
        public Boolean is_default_image;

    }
    @Data
    @NoArgsConstructor
    public static class Properties {

        public String nickname;

    }

}


