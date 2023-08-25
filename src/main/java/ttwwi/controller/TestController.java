package ttwwi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController 
{
	
    @GetMapping(value = "/oauth2/authorize/kakao")
    public void kakaoOauthRedirect(@RequestParam String code) 
    {
    	System.out.println("카카오 인가코드 : " + code);
    }


	@GetMapping("/")
	public ResponseEntity<String> mains()
	{
		return ResponseEntity.ok("hello");
	}
}
