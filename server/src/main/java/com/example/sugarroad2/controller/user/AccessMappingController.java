package com.example.sugarroad2.controller.user;

import com.example.sugarroad2.config.Security.JwtPromise;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.UsersRepository;
import com.example.sugarroad2.service.JwtService;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins="http://localhost:5173", allowedHeaders = "*",
		exposedHeaders="Authorization", allowCredentials = "true")//SOP 문제 해결과 쿠키를 전달받기 위한 설정
public class AccessMappingController {

	@Autowired
	UsersRepository userRepository;

	@Autowired
	JwtService jwtService;

	// 인증과정 없이 모든 사람이 접근 가능
//	@GetMapping("home")
//	public String home() {
//		return "<h1>home</h1>";
//	}
	
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

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Map<String, String> params,
										HttpServletResponse res) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Optional<Users> member = userRepository.findById(params.get("id"));

		boolean isPassword = encoder.matches(params.get("password"), member.get().getUserPassword());

		System.out.println("패스워드 일치 여부 : " + isPassword);

		log.info("login 실행");

		if (member.isPresent() && isPassword) {
			String id = member.get().getId();

			String token = jwtService.getToken("id", id);

			System.out.println("token : " + token);

			MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
			header.add("Authorization", token);

			return new ResponseEntity<>("로그인 성공", header, HttpStatus.OK);
			// authorization
		}

		throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/logout")
	public ResponseEntity<String> logout(@RequestHeader(value = "Authorization", required = false) String token, HttpServletResponse res) {
		log.info("logout 실행");
		MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
		header.add("Authorization", "delete");
		return new ResponseEntity<>("로그아웃 성공", header, HttpStatus.OK);
	}

	@GetMapping("/check")
	public ResponseEntity check(@RequestHeader(value = "Authorization", required = false) String token) {


        System.out.println("getClaims() 호출 : " + token);

        if (token != null && !"".equals(token)) {

			token = token.replace("Bearer ", "");

			Claims jwtclaims = null;

			try {
				byte[] secretByteKey = DatatypeConverter.parseBase64Binary(JwtPromise.SECRET);
				Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
				jwtclaims = Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
			} catch (ExpiredJwtException e) {
				System.out.println("토큰 만료");
			} catch (JwtException e) {
				System.out.println("토큰 유효하지 않음");
			}

			if (jwtclaims != null) {
				String id = jwtclaims.get("id").toString();
				Users member = userRepository.findById(id).get();
				return new ResponseEntity<>("반가워요.. " + member.getId() + " 회원님!!", HttpStatus.OK);
			}

		}

        return new ResponseEntity<>("로그인을 먼저 수행하세용~~~", HttpStatus.OK);
    }
}











