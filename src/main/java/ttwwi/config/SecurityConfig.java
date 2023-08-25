package ttwwi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import ttwwi.jwt.JwtFilter;
import ttwwi.jwt.JwtTokenProvider;
//import ttwwi.oauth2.OAuth2AuthenticationFailureHandler;
//import ttwwi.oauth2.OAuth2AuthenticationSuccessHandler;
//import ttwwi.repository.CookieAuthorizationRequestRepository;
//import ttwwi.service.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer
{
    private final long MAX_AGE_SECS = 3600;
    
	private final JwtTokenProvider jwtTokenProvider;
//    private final CustomOAuth2UserService customOAuth2UserService;
//    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
//    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
//    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception 
    {
    	httpSecurity
                .cors().and()
                .csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .rememberMe().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    	
    	httpSecurity
    			.authorizeRequests() 
            	.antMatchers("/**", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                .anyRequest().authenticated();

//    	httpSecurity
//    			.oauth2Login()
//                .authorizationEndpoint().baseUri("/oauth2/authorize")
//                .authorizationRequestRepository(cookieAuthorizationRequestRepository).and()
//                .redirectionEndpoint().baseUri("/login/oauth2/code/*").and()
//                
//                .userInfoEndpoint().userService(customOAuth2UserService).and()
//                
//                .successHandler(oAuth2AuthenticationSuccessHandler)
//                .failureHandler(oAuth2AuthenticationFailureHandler);							
//
//    	httpSecurity
//    			.logout()
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID");
//    	

        httpSecurity
        		.addFilterBefore(new JwtFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
   	
        return httpSecurity
        		.build();
    }
    
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) 
    {
    	corsRegistry
        		.addMapping("/**")
                .allowedOrigins("/*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}