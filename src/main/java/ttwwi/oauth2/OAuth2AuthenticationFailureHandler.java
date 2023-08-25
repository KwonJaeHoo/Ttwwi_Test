//package ttwwi.oauth2;
//
//import lombok.RequiredArgsConstructor;
//import ttwwi.repository.CookieAuthorizationRequestRepository;
//import ttwwi.util.CookieUtil;
//
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//import static ttwwi.repository.CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
//
//@Component
//@RequiredArgsConstructor
//public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler 
//{
//
//    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException 
//    {
//        String targetUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
//                .map(Cookie::getValue)
//                .orElse("/");
//
//        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
//                .queryParam("error", authenticationException.getLocalizedMessage())
//                .build().toUriString();
//
//        cookieAuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//    }
//}