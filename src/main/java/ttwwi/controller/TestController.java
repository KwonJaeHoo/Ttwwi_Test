package ttwwi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController 
{

	@GetMapping("/")
	public ResponseEntity<String> mains()
	{
		return ResponseEntity.ok("hello");
	}
}
