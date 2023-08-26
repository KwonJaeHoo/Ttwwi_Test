package ttwwi.controller;

import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<String> kakaoJson(@RequestBody String code) 
	{
		System.out.println("인가코드 : " + code);

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
    
//
//	       HttpHeaders kakaoTokenHeaders = new HttpHeaders();
//	       kakaoTokenHeaders.add("Authorization", "Bearer " + kakaoOauthParams.getAccess_token());
//	       kakaoTokenHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//	       HttpEntity<HttpHeaders> kakaoTokenRequest = new HttpEntity<>(kakaoTokenHeaders);
//	       ResponseEntity<String> profileResponse = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoTokenRequest, String.class);
	    
	  	
		// OkHttpClient 인스턴스 생성
		OkHttpClient client = new OkHttpClient();
		
		// Android의 Redirect URI
        String redirectUri = "kakaoc5c754213bc47db0bf3dca88211b5fe3://oauth";

     // 응답할 내용
        String responseBody = "인가코드 받기";

        // 요청 만들기
        Request request = new Request.Builder()
                .url(redirectUri)
                .build();
      try {
          // 요청 실행
          Response responseAndroid = client.newCall(request).execute();

          // 응답이 성공적인지 확인 (상태 코드 200)
          if (responseAndroid.isSuccessful()) 
          {
              // 성공 응답 처리
              return new ResponseEntity<>(responseBody, HttpStatus.OK);
          } 
          else 
          {
              // 오류 응답 처리
              return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
          }
      } 
      catch (IOException e) 
      {
          e.printStackTrace();
          // 예외 처리
          return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
      

       
      }	
	}
}