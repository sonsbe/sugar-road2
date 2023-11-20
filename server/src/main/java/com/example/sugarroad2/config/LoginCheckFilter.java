package com.example.sugarroad2.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginCheckFilter implements Filter {

    //로그인이 필요하지 않은 페이지 url 요청들
    private static final String[] whitelist = { "/home", "/signup", "/login", "/logout",
            "/search", "/store", "/post", "/review/**", "/review", "/search/**", "/store/**", "/post/**",
            "/images/**", "/js", "/js/**", "/css", "/css/**", "/comment/**", "/favicon.ico" };

    //화이트 리스트의 경우 인증 체크 스킵
    //simpleMatch : 파라미터 문자열이 특정 패턴에 매칭되는지를 검사
    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
                //매칭되지 않을 시 검증하기 때문에 부정
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();
        String redirectURL;

        HttpSession session = req.getSession(false);
        if(session != null) {
            redirectURL = (String) session.getAttribute("redirectURL");
        } else {
            redirectURL = "redirectURL이 없음";
        }

        System.out.println("인증 체크 필터 시작");
        System.out.println("인증 체크 필터 시작 전 리퀘스트 URI : " + requestURI);
        System.out.println("인증 체크 필터 시작 전 세션 속 리다이렉트 URI : " + redirectURL);

        //whitelist의 uri는 검증하지 않고 통과
        //isLoginCheckPath 메서드에서 매칭되지 않는 경우만 가져온다
        //인증체크 로직 시작
        if(isLoginCheckPath(requestURI)){
            System.out.println("인증 체크 로직 실행 : " + requestURI);
            //세션을 불러옴
            session = req.getSession(false);
            System.out.println("불러온 세션에 저장된 URL : " + requestURI);

            if(requestURI.equals("/login.html")){
                requestURI = "/home";
            }

            if(session == null || session.getAttribute("nowLogin") == null){
                //로그인 되지 않으면
                System.out.println("인증되지 않은 사용자 요청");
                //로그인 화면으로 리다이렉트
                //사용자가 원래 가려고 했던 URI를 파라미터 값으로 기억했다가 로그인하면 이 곳으로 이동
                res.sendRedirect("/login?redirectURL=" + requestURI);
                return;
            }

            session.setAttribute("redirectURL", redirectURL);
        }
        //로그인이 되어있으면 다음 단계로 넘어간다
        chain.doFilter(request, response);
    }

}
