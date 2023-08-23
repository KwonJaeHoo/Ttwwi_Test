package ttwwi.controller;

import lombok.RequiredArgsConstructor;
import ttwwi.dto.AccessTokenDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController 
{	
	@PostMapping("/oauth2/authorize")
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