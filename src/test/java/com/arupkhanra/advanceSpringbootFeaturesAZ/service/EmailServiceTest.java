package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    @Disabled
    void testSendMail(){
        System.out.println("hi arupkhanra853");
        emailService.sendMail("sumitraghorai90@gmail.com",
                "TESTING-SUMITRA_GHORAI",
                "Do you like me? If you like me," +
                        " then reply to me. If you don't like me, still reply to me!");
    }
}
