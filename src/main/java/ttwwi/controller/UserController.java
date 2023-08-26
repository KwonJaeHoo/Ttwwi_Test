package ttwwi.controller;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ttwwi.dto.AuthorizedCodeDto;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/login/oauth2/code")
@RequiredArgsConstructor

public class UserController 
{	
//	@GetMapping(value = "/kakao")
//	public void kakaoOauthRedirect(@RequestParam String code)
//	{
//		System.out.println("/login/oauth2/code/kakao 카카오 인가코드 : " + code);	
//	}
	
	@PostMapping("/kakao")
	public ResponseEntity<Object> kakaoJson(@RequestBody AuthorizedCodeDto authorizedCode) 
	{
		System.out.println("인가코드 : " + authorizedCode);

		//member를 body로 지정해서, member 객체를 JSON으로 변환한다.
	    return ResponseEntity.status(HttpStatus.OK).body(authorizedCode);
	}
	
	@PostMapping("aa")
	public ResponseEntity<String> kakao(@RequestBody String code)
	{
		// 카카오에 POST방식으로 key=value 데이터를 요청함. RestTemplate를 사용하면 요청을 편하게 할 수 있다.
	  	RestTemplate restTemplate = new RestTemplate();
	  	   // HttpHeader 오브젝트 생성
	       HttpHeaders kakaoCodeHeaders = new HttpHeaders();
	       kakaoCodeHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

	       // HttpBody 오브젝트 생성
	       MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	       	params.add("grant_type", "authorization_code");
	       	params.add("client_id", "{client_id}");
	       	params.add("redirect_uri", "http://43.202.39.197:8000/login/oauth2/code/kakao");
	       	params.add("code", code);
	       	params.add("client_secret", "{secret_code}");

	       // HttpHeader와 HttpBody를 HttpEntity에 담기 (why? rt.exchange에서 HttpEntity객체를 받게 되어있다.)
	       HttpEntity<MultiValueMap<String, String>> kakaoCodeRequest = new HttpEntity<>(params, kakaoCodeHeaders);

	       // HTTP 요청 - POST방식 - response 응답 받기
	       ResponseEntity<String> response = restTemplate.exchange("https://kauth.kakao.com/oauth/token",  HttpMethod.POST, kakaoCodeRequest, String.class);
	       
	       	return null;
//	       HttpHeaders kakaoTokenHeaders = new HttpHeaders();
//	       kakaoTokenHeaders.add("Authorization", "Bearer " + kakaoOauthParams.getAccess_token());
//	       kakaoTokenHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//	       HttpEntity<HttpHeaders> kakaoTokenRequest = new HttpEntity<>(kakaoTokenHeaders);
//	       ResponseEntity<String> profileResponse = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoTokenRequest, String.class);
	}
}