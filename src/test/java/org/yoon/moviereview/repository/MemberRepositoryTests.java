package org.yoon.moviereview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.moviereview.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder()
                    .email("r"+i+"@yoon.org")
                    .pw("1111")
                    .nickname("reviewer"+i)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void insertOneMembers(){

            Member member = Member.builder()
                    .email("r"+9999+"@yoon.org")
                    .pw("1111")
                    .nickname("reviewer"+9999)
                    .build();

            memberRepository.save(member);

    }



    @Commit
    @Transactional
    @Test
    public void testDeleteMember(){
        Long mid = 1L;

        Member member = Member.builder().mid(mid).build();

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
    }
}
