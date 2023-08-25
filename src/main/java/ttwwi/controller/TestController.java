package ttwwi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController 
{
	@GetMapping("/Ok")
	public ResponseEntity<String> main()
	{
		return ResponseEntity.ok("hello");
	}
	@GetMapping("/")
	public ResponseEntity<String> mains()
	{
		return ResponseEntity.ok("hello");
	}
}
