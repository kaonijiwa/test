package com.example.provider;

import com.example.provider.pojo.Student;
import com.example.provider.service.DemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProviderApplicationTests {

    @Autowired
    private DemoService demoService;
    @Test
    void contextLoads() {

        long startTime02 = System.currentTimeMillis();
        List<Student> list02 = demoService.simpleSelectAllByName("b");
        long endTime02 = System.currentTimeMillis();
        System.out.println(list02.size());
        System.out.println(endTime02-startTime02);

//        System.out.println("******************");
//        long startTime = System.currentTimeMillis();
//        List<Student> list01 = demoService.selectAllByName("b");
//        long endTime = System.currentTimeMillis();
//        System.out.println(list01.size());
//        System.out.println(endTime-startTime);
//
//        System.out.println("******************");
//        long startTime03 = System.currentTimeMillis();
//        List<Student> list03 = demoService.simpleSelectAllByName("b");
//        long endTime03= System.currentTimeMillis();
//        System.out.println(list03.size());
//        System.out.println(endTime03-startTime03);



    }

}
