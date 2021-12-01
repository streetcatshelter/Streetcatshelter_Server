package streetcatshelter.discatch.domain.oauth.social;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NaverUserInfo implements UserInfo{
    public String profile_image;
    public String nickname;
    public String name;
    public String id;
    public String email;

    @Override
    public String getUserId() {
        return this.id;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public String getProfileImageUrl() {
        return this.profile_image;
    }
}
