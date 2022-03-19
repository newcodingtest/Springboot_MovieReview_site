package org.yoon.moviereview.security.filter;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)//필터중 가장 먼저 동작하도록하는 어노테이션
public class CORSFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "");
        response.setHeader("Access-Control-Allow-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Key, Authorization");

        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_OK);
        }else{
            filterChain.doFilter(request, response);
        }
    }

    //프론트 단에서 /notes/*/** 형식의 api 방식 주소를 요청하면? ajax로
   /*
    $(".btn").click(function(){
       $.ajax({
            beforeSend: function(request){
                request.setRequestHeader("Authorization", 'Bearer '+jwtValue);
        },
        dataType: "json",
        url: 'http://localhost:8080/notes/all',
        data: {email: 'user10@yoon.org'},
        success: function(arr){
           console.log(arr);
        }
       });
    });

    */
}
