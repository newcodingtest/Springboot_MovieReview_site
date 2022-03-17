package org.yoon.moviereview.security.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.yoon.moviereview.dto.ClubAuthMemberDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); //리다이렉트 클래스

    private PasswordEncoder passwordEncoder;

    public ClubLoginSuccessHandler(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("----------------------------------------------");
        log.info("onAuthenticationSuccess ");

        ClubAuthMemberDTO authMember = (ClubAuthMemberDTO)authentication.getPrincipal();

        boolean fromSocial = authMember.isFromSocial();

        log.info("Need Modify Member?" + fromSocial);
        log.info("비밀번호: "+  passwordEncoder.encode(authMember.getPassword()));

        boolean passwordResult = passwordEncoder.matches("1111", passwordEncoder.encode(authMember.getPassword()));

        //소셜로그인이 있는 사용자가 소셜로그인 정보로 form에 로그인을 시도하게되면 회원정보 수정페이지로 유도한다. => 비밀번호 재지정=>소셜로그인 비밀번호 저장시 문제가 될수 있음
        if(fromSocial && passwordResult){
            redirectStrategy.sendRedirect(request, response, "/member/modify?from=social");
        }

    }
}
