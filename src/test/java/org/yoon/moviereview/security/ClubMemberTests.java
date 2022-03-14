package org.yoon.moviereview.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.yoon.moviereview.entity.ClubMember;
import org.yoon.moviereview.entity.ClubMemberRole;
import org.yoon.moviereview.repository.ClubMemberRepository;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTests {

    @Autowired
    private ClubMemberRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void insertDummies(){

        IntStream.rangeClosed(1,100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user"+i+"@yoon.org")
                    .name("사용자"+i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER);
            if(i>80){
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }
            if(i>90){
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }

            repository.save(clubMember);
        });
    };

    @Test
    public void insertOneDummies(){

            ClubMember clubMember = ClubMember.builder()
                    .email("user"+9999+"@yoon.org")
                    .name("사용자"+9999)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER);

            repository.save(clubMember);

    };

    @Test
    public void 비밀번호인코딩테스트(){
        String password = "$2a$10$YQI1LNYjwM/0G/dhTSkf0eX.zuDacy5LbIORRhtWO.2NCh1GaURC2";

        Assertions.assertEquals("true",passwordEncoder.matches(password,"1111"));

    }


    @Test
    public void testRead(){

        Optional<ClubMember> result = repository.findByEmail("user95@yoon.org",false);

        ClubMember clubMember = result.get();
        System.out.println(clubMember);
    }
}
