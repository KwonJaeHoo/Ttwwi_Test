package ttwwi.entity;

import lombok.*;
import ttwwi.enums.AuthProvider;
import ttwwi.enums.Role;
import ttwwi.oauth2.OAuth2UserInfo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Table(name = "members")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @NotNull
    private String email;
    
    @NotNull
    private String name;

    private String oauth2Id;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    private String imageUrl;
    
//    @OneToMany(mappedBy = "contributor")
//    private List<Trip> trips = new ArrayList<>();
    
    
    public Member update(OAuth2UserInfo oAuth2UserInfo) 
    {
        this.name = oAuth2UserInfo.getName();
        this.oauth2Id = oAuth2UserInfo.getOAuth2Id();
        this.imageUrl = oAuth2UserInfo.getImageUrl();

        return this;
    } 
}
