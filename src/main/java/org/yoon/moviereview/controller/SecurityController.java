package org.yoon.moviereview.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yoon.moviereview.dto.ClubAuthMemberDTO;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SecurityController {

    @PreAuthorize("permetAll()")
    @GetMapping("/all")
    public void  exAll(){
        log.info("exAll...");
    }

    @GetMapping("/member")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){
        log.info("exMember...");
        log.info(clubAuthMember);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public void exAdmin(){
        log.info("exAdmin...");
    }

    @PreAuthorize("#clubAuthMember !=null && #clubAuthMember.username eq \"user95@yoon.org\"")
    @GetMapping("/exOnly")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDTO clubAuthMember){
        log.info("exMemberOnly..........");
        log.info(clubAuthMember);

        return "/sample/admin";
    }

}
