package streetcatshelter.discatch.domain.oauth.social;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleUserInfo implements UserInfo
{
    public String id;
    public String email;
    public Boolean verified_email;
    public String name;
    public String given_name;
    public String family_name;
    public String picture;
    public String locale;

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
        return this.picture;
    }
}
