package org.yoon.moviereview.config;


import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.yoon.moviereview.security.filter.ApiCheckFilter;
import org.yoon.moviereview.security.filter.ApiLoginFilter;
import org.yoon.moviereview.security.handler.ApiLoginFailHandler;
import org.yoon.moviereview.security.handler.ClubLoginSuccessHandler;
import org.yoon.moviereview.security.util.JWTUtil;
import org.yoon.moviereview.service.ClubUserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)//어노테이션 기반의 접근제한 설정, securedEnabled: 예전버전의 @Secure 사용가능여부,
// prePostEnabled: @PreAuthorize 사용여부
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClubUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http)throws Exception {

       // http.authorizeRequests()
       //         .antMatchers("/sample/all").permitAll()
       //         .antMatchers("/sample/member").hasRole("USER");
                //.antMatchers("/sample/member").permitAll();

        http.formLogin();
        http.csrf().disable();
        http.logout();

        http.oauth2Login().successHandler(successHandler());
        http.rememberMe().tokenValiditySeconds(60*60*27*7)
                .userDetailsService(userDetailsService);

        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public ClubLoginSuccessHandler successHandler(){
        return new ClubLoginSuccessHandler(passwordEncoder());
    }

    @Bean
    public ApiCheckFilter apiCheckFilter(){
        return new ApiCheckFilter("/notes/**/*",jwtUtil());
    }

    @Bean
    public ApiLoginFilter apiLoginFilter()throws Exception{
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login",jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager());

        apiLoginFilter.setAuthenticationFailureHandler(new ApiLoginFailHandler());

        return apiLoginFilter;
    }

    @Bean
    public JWTUtil jwtUtil(){
        return new JWTUtil();
    }

}
