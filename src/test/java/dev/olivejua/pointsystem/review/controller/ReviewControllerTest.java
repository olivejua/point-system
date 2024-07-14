package dev.olivejua.pointsystem.review.controller;

import org.junit.jupiter.api.Test;

public class ReviewControllerTest {


//    @Test
//    void 사용자는_상품리뷰를_작성할_수_있다() {
//        //given
//        TestContainer testContainer = TestContainer.builder()
//                .clockHolder(() -> )
//                .build();
//
//        //when
//
//
//        //then
//
//    }


    @Test
    void name() {
        String str1 = "Hello, World!";
        String str2 = "Hello, World!";
        String str3 = new String("Hello, World!");

        System.out.println("str1 == str2: " + (str1 == str2)); // true
        System.out.println("str1 == str3: " + (str1 == str3)); // false
        System.out.println("str1.equals(str3): " + str1.equals(str3)); // true
    }
}
