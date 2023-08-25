//package ttwwi.oauth2;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import ttwwi.dto.UserResponseDto;
//import ttwwi.jwt.JwtTokenProvider;
//import ttwwi.repository.CookieAuthorizationRequestRepository;
//import ttwwi.util.CookieUtil;
//
//import org.springframework.beans.factory.annotation.Value;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.net.URI;
//
//import java.util.Optional;
//
//import static ttwwi.repository.CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler 
//{
//
//    @Value("${jwt.authorizedRedirectUri}")
//    private String redirectUri;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException 
//    {
//        String targetUrl = determineTargetUrl(request, response, authentication);
//
//        if (response.isCommitted()) 
//        {
//            log.debug("Response has already been committed.");
//            return;
//        }
//        clearAuthenticationAttributes(request, response);
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//    }
//
//    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) 
//    {
//        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
//                .map(Cookie::getValue);
//
//        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) 
//        {
//            throw new RuntimeException("redirect URIs are not matched.");
//        }
//        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
//
//        //JWT 생성
//        UserResponseDto tokenInfo = jwtTokenProvider.generateToken(authentication);
//        
//        // JWT 토큰을 Authorization 헤더에 포함하여 전달
//        response.setHeader("Authorization", "Bearer " + tokenInfo.getAccessToken());
//        return targetUrl;
//    }
//    
//    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) 
//    {
//        super.clearAuthenticationAttributes(request);
//        cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
//    }
//
//    private boolean isAuthorizedRedirectUri(String uri) 
//    {
//        URI clientRedirectUri = URI.create(uri);
//        URI authorizedUri = URI.create(redirectUri);
//
//        if (authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) && authorizedUri.getPort() == clientRedirectUri.getPort()) 
//        {
//            return true;
//        }
//        return false;
//    }
//}