package org.yoon.moviereview.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yoon.moviereview.security.util.JWTUtil;

public class JWTTests {

    private JWTUtil jwtUtil;

    @BeforeEach
    public void setUp(){
        jwtUtil = new JWTUtil();
    }

    @Test
    void testEncode() throws Exception {

        String email = "user91@yoon.org";

        String str = jwtUtil.generateToken(email);

        System.out.println(str);
        //https://jwt.io 를 통해 검증
    }

    @Test
    void testValidate() throws Exception {

        String email = "user91@yoon.org";

        String str = jwtUtil.generateToken(email);

        Thread.sleep(5000);

        String resultEmail = jwtUtil.validateAndExtract(str);

        System.out.println("resultEmail = " + resultEmail);

    }
}
