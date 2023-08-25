package ttwwi.repository;

import com.nimbusds.oauth2.sdk.util.StringUtils;

import ttwwi.util.CookieUtil;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
인증 요청을 쿠키에 저장하고 검색하는 기능
OAuth2 프로토콜은 CSRF 공격을 방지하기 위해 STATE 매개 변수 사용을 권장한다.
인증 과정 중 앱은 인증 요청에 매개변수를 담고, OAuth2 공급자는 OAuth2 콜백에서 변경되지 않은 해당 매개 변수를 반환
앱은 OAuth2 공급자로부터 반환된 매개 변수의 값을 비교
일치하지 않으면 인증 요청 거부
 */
@Component
public class CookieAuthorizationRequestRepository implements AuthorizationRequestRepository 
{

    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final int COOKIE_EXPIRE_SECONDS = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) 
    {    	
        return CookieUtil.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) 
    {
        if (authorizationRequest == null) 
        {
            CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
            return;
        }
   	
        CookieUtil.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, CookieUtil.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);

        if (StringUtils.isNotBlank(redirectUriAfterLogin)) 
        {
        	System.out.println(OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        	System.out.println(REDIRECT_URI_PARAM_COOKIE_NAME);
            CookieUtil.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, COOKIE_EXPIRE_SECONDS);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) 
    {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) 
    {
        CookieUtil.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtil.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    }
}