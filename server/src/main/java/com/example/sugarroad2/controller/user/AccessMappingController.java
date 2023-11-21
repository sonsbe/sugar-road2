package com.example.sugarroad2.controller.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.sugarroad2.config.auth.NowUserDetails;
import com.example.sugarroad2.config.jwt.JwtAuthorizationFilter;
import com.example.sugarroad2.config.jwt.JwtProperties;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.UsersRepository;
import com.example.sugarroad2.service.JwtService;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
//@CrossOrigin(origins="http://localhost:5173", allowedHeaders = "*",
//		exposedHeaders="Authorization", allowCredentials = "true")//SOP 문제 해결과 쿠키를 전달받기 위한 설정
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

	@GetMapping("/check")
	public ResponseEntity check(@RequestHeader(value = "Authorization", required = false) String token) {

		log.info("check 수행");

        if (token != null && !"".equals(token)) {

			token = token.replace("Bearer ", "");

			System.out.println("token : " + token);

			try {
				// 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
				// 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는 loadByUsername이 호출됨.
				String tokenUser = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
						.build()
						.verify(token)
						.getClaim("id")
						.asString();

				System.out.println("토큰 속 ID 정보 : " + tokenUser);

				if(tokenUser != null) {
					Users user = userRepository.findById(tokenUser).get();
					System.out.println("현재 로그인 중인 유저 : " + user);
					// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
					// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
					NowUserDetails principalDetails = new NowUserDetails(user);
					//System.out.println(principalDetails.getAuthorities());
					Authentication authentication = new UsernamePasswordAuthenticationToken(
									principalDetails, //나중에 컨트롤러에서 ID해서 쓸 때 사용하기 편함.
									null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
									principalDetails.getAuthorities());

					// 강제로 시큐리티의 세션에 접근하여 값 저장
					//SecurityContextHolder.getContext().setAuthentication(authentication);

					return new ResponseEntity<>("반가워요.. " + user.getId() + " 회원님!!", HttpStatus.OK);
				}
			} catch (ExpiredJwtException e) {
				System.out.println("토큰 만료");
			} catch (JwtException e) {
				System.out.println("토큰 유효하지 않음");
			}
		}
        return new ResponseEntity<>("로그인을 먼저 수행하세용~~~", HttpStatus.OK);
    }
}











