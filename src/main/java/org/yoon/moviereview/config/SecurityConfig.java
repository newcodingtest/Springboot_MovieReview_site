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
import org.yoon.moviereview.security.handler.ClubLoginSuccessHandler;
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

    }

    @Bean
    public ClubLoginSuccessHandler successHandler(){
        return new ClubLoginSuccessHandler(passwordEncoder());
    }


}
