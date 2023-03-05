package com.example.flowerapp;


import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

//Service layer testing 

    @Test
   void starterTest()
   {
    boolean result=true;
    assertEquals(result, "working hello".contains("working"));
   }


}
