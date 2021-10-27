package streetcatshelter.discatch.domain.chat.dto;


import lombok.Data;
import streetcatshelter.discatch.domain.user.domain.User;

@Data
public class LoginResponseDto {

    private Long userId;
    private String token;
    private String username;
    private String profileImage;

    public LoginResponseDto(User user, String token){
        this.userId = user.getUserSeq();
        this.token = token;
        this.username = user.getUsername();
        this.profileImage = user.getProfileImageUrl();

    }
}
