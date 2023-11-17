package com.example.sugarroad2.controller.user;

import com.example.sugarroad2.config.jwt.JwtAuthenticationFilter;
import com.example.sugarroad2.repository.UsersRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin(origins="http://localhost:5173", allowedHeaders = "*",
		exposedHeaders="Authorization", allowCredentials = "true")//SOP 문제 해결과 쿠키를 전달받기 위한 설정
public class AccessMappingController {

	@Autowired
	UsersRepository userRepository;

	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	// 인증과정 없이 모든 사람이 접근 가능
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	// 유저, 매니저, 어드민 접근 가능
//	@GetMapping("user")
//	@PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
//	public String user(Authentication authentication) {
//		NowUserDetails principal = (NowUserDetails) authentication.getPrincipal();
//
//		System.out.println("principal username : " + principal.getUser().getId());
//		System.out.println("principal password : " + principal.getUser().getUserPassword());
//
//		return "<h1>user</h1>";
//	}
	
	// 매니저, 어드민 접근 가능
//	@GetMapping("manager/reports")
//	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
//	public String reports() {
//		return "<h1>reports</h1>";
//	}
	
	// 어드민만 접근 가능
//	@GetMapping("admin/users")
//	@PreAuthorize("hasAnyRole('ADMIN')")
//	public List<Users> users(){
//		return userRepository.findAll();
//	}

	@PostMapping("login")
	public ResponseEntity<String> login(HttpRequest res) {

		HttpHeaders heders = new HttpHeaders();

		System.out.println("http 헤더를 반환합니다");

		return new ResponseEntity<>("로그인 성공", heders, HttpStatus.OK);
			// authorization

	}

	@GetMapping("logout")
	public ResponseEntity<String> logout(@RequestHeader(value = "Authorization", required = false) String token, HttpServletResponse res) {

		MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
		header.add("Authorization", "delete");
		return new ResponseEntity<>("로그아웃 성공", header, HttpStatus.OK);
	}

//	@GetMapping("check")
//	public ResponseEntity check(@RequestHeader(value = "Authorization", required = false) String token) {
//
//		Claims claims = jwtService.getClaims(token);
//
//		if (claims != null) {
//			int id = Integer.parseInt(claims.get("id").toString());
//			JwtMember member = memberRepository.findById(id).get();
//			return new ResponseEntity<>("반가워요.. "+member.getEmail()+" 회원님!!", HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>("로그인을 먼저 수행하세용~~~", HttpStatus.OK);
//	}
}











