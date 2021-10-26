package streetcatshelter.discatch.domain.oauth.social;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NaverUserInfo {
    public String profile_image;
    public String nickname;
    public String name;
    public String id;
    public String email;
}
