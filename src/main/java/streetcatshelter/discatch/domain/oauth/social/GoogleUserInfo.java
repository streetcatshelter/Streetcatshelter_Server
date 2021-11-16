package streetcatshelter.discatch.domain.oauth.social;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleUserInfo
{
    public String id;
    public String email;
    public Boolean verified_email;
    public String name;
    public String given_name;
    public String family_name;
    public String picture;
    public String locale;
}
