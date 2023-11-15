package com.example.sugarroad2.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable() //CSRF 보안방식
                .authorizeHttpRequests(request -> request
                        .dispatcherTypeMatchers(DispatcherType.FORWARD)
                        .permitAll()
                        //화이트리스트 임의로 전체 URL을 허용했습니다
                        .requestMatchers("/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(login -> login //로그인 View와 대응하는 코드입니다
                        .loginPage("/view/login") //View URL
                        .loginProcessingUrl("/login-process") //폼의 action속성
                        .usernameParameter("userid") //폼의 ID input name속성
                        .passwordParameter("pw") //폼의 password input name속성
                        .defaultSuccessUrl("/view/memberpage", true)
                        //로그인 성공 시 이동하는 페이지
                        .permitAll()
                )
                .logout(withDefaults()); //로그아웃 시 이동할 페이지를 설정합니다

        return http.build();
    }
}
