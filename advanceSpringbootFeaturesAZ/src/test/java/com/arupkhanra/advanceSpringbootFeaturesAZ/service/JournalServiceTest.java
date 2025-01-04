//package com.arupkhanra.advanceSpringbootFeaturesAZ.service;
//
//import com.arupkhanra.advanceSpringbootFeaturesAZ.AdvanceSpringbootFeaturesAzApplication;
//import com.arupkhanra.advanceSpringbootFeaturesAZ.repository.UserRepository;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.junit.jupiter.api.Assertions.*;
//
////@SpringBootTest(classes = AdvanceSpringbootFeaturesAzApplication.class)
// class JournalServiceTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Disabled
//    @Test
//     void testFindByUsername(){
//
//        assertEquals(4,2+2);
//        assertNotNull(userRepository.findByUserName("arup"),"fall for ");
//    }
//    @ParameterizedTest
//    @CsvSource({
//            "1,2,3",
//            "2,10,12",
//            "2,12,10"
//    })
//
//    void test(int a, int b, int expected){
//
//        assertEquals(expected,a+b,"fail for "+a+b);
//    }
//
//}
