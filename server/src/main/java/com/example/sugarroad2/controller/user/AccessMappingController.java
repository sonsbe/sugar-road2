package com.example.sugarroad2.controller.user;

import com.example.sugarroad2.config.auth.NowUserDetails;
import org.springframework.security.core.userdetails.User;
import com.example.sugarroad2.model.entity.Users;
import com.example.sugarroad2.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AccessMappingController {
	
	private final UsersRepository userRepository;
	
	// 인증과정 없이 모든 사람이 접근 가능
	@GetMapping("home")
	public String home() {
		return "<h1>home</h1>";
	}
	
	// 유저, 매니저, 어드민 접근 가능
	@GetMapping("user")
	@PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
	public String user(Authentication authentication) {
		NowUserDetails principal = (NowUserDetails) authentication.getPrincipal();

		System.out.println("principal username : " + principal.getUser().getId());
		System.out.println("principal password : " + principal.getUser().getUserPassword());
		
		return "<h1>user</h1>";
	}
	
	// 매니저, 어드민 접근 가능
	@GetMapping("manager/reports")
	@PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
	public String reports() {
		return "<h1>reports</h1>";
	}
	
	// 어드민만 접근 가능
	@GetMapping("admin/users")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<Users> users(){
		return userRepository.findAll();
	}

	@PostMapping("login")
	public String login() {
		return "토큰 발행 완료";
	}
}











