package streetcatshelter.discatch.domain.user.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import streetcatshelter.discatch.domain.TimeStamped;
import streetcatshelter.discatch.domain.oauth.entity.ProviderType;
import streetcatshelter.discatch.domain.oauth.entity.RoleType;
import streetcatshelter.discatch.domain.oauth.social.GoogleUserInfo;
import streetcatshelter.discatch.domain.user.dto.UserInformationRequestDto;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends TimeStamped implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userSeq;

    @Column
    private String userId;

    @Column
    private String userRandomId;

    @Column(nullable = true)
    private String username;

    @Column
    private String password;

    @Column(nullable = true, unique = true)
    private String nickname;

    @JsonManagedReference
    @OneToMany(mappedBy = "user",  cascade = {CascadeType.REMOVE})
    private List<UserLocation> userLocationList = new ArrayList<>();

    @Column(nullable = true)
    private String profileUrl;

    @Column(nullable = true)
    private String email;

    @Column(columnDefinition = "default 3")
    private UserLevel userLevel;

    @Column(nullable = true)
    private int score;

    @Column(nullable = true)
    private int scoreLeft;

    @Column
    private String emailVerifiedYn;

    @Column
    private String profileImageUrl;

    @Column
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public User(
            @NotNull String userId,
            String username,
            @NotNull String email,
            @NotNull String emailVerifiedYn,
            @NotNull String profileImageUrl,
            @NotNull ProviderType providerType,
            @NotNull RoleType roleType,
            UserLevel userLevel) {
        this.userId = userId;
        this.username = username;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
        this.userLevel = userLevel;
        this.userRandomId = UUID.randomUUID().toString();
    }
    public User(
            @NotNull String userId,
            String username,
            @NotNull String nickname,
            @NotNull String email,
            @NotNull String emailVerifiedYn,
            @NotNull String profileImageUrl,
            @NotNull ProviderType providerType,
            @NotNull RoleType roleType,
            UserLevel userlevel
    ) {
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
        this.userLevel = userlevel;
        this.userRandomId = UUID.randomUUID().toString();
    }

    public static User googleUserCreate(GoogleUserInfo googleUserInfo){
        return new User(
                googleUserInfo.getId(),
                googleUserInfo.getName(),
                googleUserInfo.getEmail(),
                "Y",
                googleUserInfo.getPicture(),
                ProviderType.GOOGLE,
                RoleType.USER,
                UserLevel.아깽이

                );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUserSeq(), user.getUserSeq()) && Objects.equals(getUserId(), user.getUserId()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getEmailVerifiedYn(), user.getEmailVerifiedYn()) && Objects.equals(getProfileImageUrl(), user.getProfileImageUrl()) && getProviderType() == user.getProviderType() && getRoleType() == user.getRoleType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserSeq(), getUserId(), getUsername(), getPassword(), getEmail(), getEmailVerifiedYn(), getProfileImageUrl(), getProviderType(), getRoleType());
    }

    public void update(UserInformationRequestDto requestDto) {
        this.nickname = requestDto.getNickname();
        if(requestDto.getProfileUrl() != null) {
            this.profileUrl = requestDto.getProfileUrl();
        }
    }

    public void addUserLocationList(List<UserLocation> userLocationList) {
        this.userLocationList = userLocationList;
    }

    public void setUserLevel(UserLevel userLevel) {
        this.userLevel = userLevel;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setScoreLeft(int scoreLeft) {
        this.scoreLeft = scoreLeft;
    }

    public void updateUserLevel() {
        final UserLevel nextLevel = this.userLevel.nextLevel();
        if(nextLevel == null){
            throw new IllegalStateException(this.userLevel + "은 업그레이드가 불가능합니다.");
        }else{
            this.userLevel = nextLevel;
        }
    }
}
