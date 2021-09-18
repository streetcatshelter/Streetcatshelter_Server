package streetcatshelter.discatch.domain;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import streetcatshelter.discatch.oauth.entity.ProviderType;
import streetcatshelter.discatch.oauth.entity.RoleType;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class User extends TimeStamped{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userSeq;

    @Column
    private String userId;

    @Column(nullable = false)
    private String username;

    @Column
    private String password;


    @Column(nullable = true)
    private String email;


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
            @NotNull String username,
            @NotNull String email,
            @NotNull String emailVerifiedYn,
            @NotNull String profileImageUrl,
            @NotNull ProviderType providerType,
            @NotNull RoleType roleType
    ) {
        this.userId = userId;
        this.username = username;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerifiedYn = emailVerifiedYn;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
    }
}
