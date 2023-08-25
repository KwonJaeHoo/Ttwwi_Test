package ttwwi.controller;

import lombok.RequiredArgsConstructor;
import ttwwi.dto.AccessTokenDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login/oauth2/code")
@RequiredArgsConstructor

public class UserController 
{	

	    @GetMapping(value = "/kakao")
	    public void kakaoOauthRedirect(@RequestParam String code) 
	    {
	    	System.out.println("카카오 인가코드 : " + code);
	    }
    
		@PostMapping("/kakao")
	    public ResponseEntity<String> kakaoJson(@RequestBody AccessTokenDto accessToken) 
	    {
	        System.out.println("Received JSON from frontend: " + accessToken.getAccessToken());
	               
	        if(accessToken.getAccessToken().equals(null))
	        {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("faileds");
	        }
	        return ResponseEntity.status(HttpStatus.OK).body("success");   
	    }
}