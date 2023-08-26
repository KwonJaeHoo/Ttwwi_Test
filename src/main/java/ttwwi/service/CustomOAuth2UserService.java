package ttwwi.service;

import lombok.RequiredArgsConstructor;
import ttwwi.entity.Member;
import ttwwi.enums.AuthProvider;
import ttwwi.enums.Role;
import ttwwi.oauth2.OAuth2UserInfo;
import ttwwi.oauth2.OAuth2UserInfoFactory;
import ttwwi.oauth2.UserPrincipal;
import ttwwi.repository.MemberRepository;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> 
{

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException 
    {
    	System.out.println("3: " + oAuth2UserRequest.toString());
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(oAuth2UserRequest);

        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    protected OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) 
    {
    	System.out.println("4: " + oAuth2User.toString());
    	System.out.println("5: " + oAuth2UserRequest.toString());
    	
        //OAuth2 로그인 플랫폼 구분
        AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());

        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) 
        {
            throw new RuntimeException("Email not found from OAuth2 provider");
        }

        Member userEntity = memberRepository.findByEmail(oAuth2UserInfo.getEmail()).orElse(null);
        //이미 가입된 경우
        if (userEntity != null) 
        {
            if (!userEntity.getAuthProvider().equals(authProvider)) 
            {
                throw new RuntimeException("Email already signed up.");
            }
            userEntity = updateUser(userEntity, oAuth2UserInfo);
        }
        //가입되지 않은 경우
        else 
        {
            userEntity = registerUser(authProvider, oAuth2UserInfo);
        }

        return UserPrincipal.create(userEntity, oAuth2UserInfo.getAttributes());
    }

    private Member registerUser(AuthProvider authProvider, OAuth2UserInfo oAuth2UserInfo) 
    {
        Member userEntity = Member.builder()
        		
                .email(oAuth2UserInfo.getEmail())
                .name(oAuth2UserInfo.getName())
                .oauth2Id(oAuth2UserInfo.getOAuth2Id())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .authProvider(authProvider)
                .role(Role.ROLE_USER)
                .build();

        return memberRepository.save(userEntity);
    }

    private Member updateUser(Member userEntity, OAuth2UserInfo oAuth2UserInfo) 
    {

        return memberRepository.save(userEntity.update(oAuth2UserInfo));
    }
}