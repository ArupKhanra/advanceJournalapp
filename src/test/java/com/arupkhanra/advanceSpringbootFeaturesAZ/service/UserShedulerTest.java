package com.arupkhanra.advanceSpringbootFeaturesAZ.service;

import com.arupkhanra.advanceSpringbootFeaturesAZ.scheduler.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
 class UserShedulerTest {

    @Autowired
    UserScheduler userScheduler;
   // @Test
    void fetchUsersAndSendSaMail(){

        userScheduler.fetchUsersAndSendSaMail();
    }
}
