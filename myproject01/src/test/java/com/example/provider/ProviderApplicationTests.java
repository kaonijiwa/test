package com.example.provider;

import com.example.provider.mapper.StudentMapper;
import com.example.provider.pojo.Student;
import com.example.provider.pojo.StudentExample;
import com.example.provider.service.DemoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

@SpringBootTest
class ProviderApplicationTests {

    @Autowired
    private DemoService demoService;
    @Autowired
    private StudentMapper studentMapper;

    @Test
    void contextLoads() {

        long startTime02 = System.currentTimeMillis();
        List<Student> list02 = demoService.simpleSelectAllByName("b");
        long endTime02 = System.currentTimeMillis();
        System.out.println(list02.size());
        System.out.println(endTime02 - startTime02);

    }

    @Test
    void test() {
        long time01 = System.currentTimeMillis();
        StudentExample studentExample01 = new StudentExample();
        studentExample01.createCriteria().andCarLike("li");
        List<Student> list01 = studentMapper.selectByExample(studentExample01);

        StudentExample studentExample02 = new StudentExample();
        studentExample02.createCriteria().andNameLike("li");
        List<Student> list02 = studentMapper.selectByExample(studentExample02);

        StudentExample studentExample03 = new StudentExample();
        studentExample03.createCriteria().andAgeBetween(40000, 50000);
        List<Student> list03 = studentMapper.selectByExample(studentExample03);
        long time02 = System.currentTimeMillis();
        System.out.println(list01.size() + list02.size() + list03.size());
        System.out.println(time02 - time01);
    }

    @Test
    public void testCompletableFuture() throws ExecutionException, InterruptedException {
        long time01 = System.currentTimeMillis();
        CompletableFuture<List<Student>> completableFuture01 = CompletableFuture.supplyAsync(() -> {
            StudentExample studentExample01 = new StudentExample();
            studentExample01.createCriteria().andCarLike("li");
            List<Student> list = studentMapper.selectByExample(studentExample01);
            return list;
        });
        CompletableFuture<List<Student>> completableFuture02 = CompletableFuture.supplyAsync(() -> {
            StudentExample studentExample02 = new StudentExample();
            studentExample02.createCriteria().andNameLike("li");
            return studentMapper.selectByExample(studentExample02);
        });
        CompletableFuture<List<Student>> completableFuture03 = CompletableFuture.supplyAsync(() -> {
            StudentExample studentExample03 = new StudentExample();
            studentExample03.createCriteria().andAgeBetween(40000, 50000);
            return studentMapper.selectByExample(studentExample03);
        });

        List<Student> list01 = completableFuture01.get();
        List<Student> list02 = completableFuture02.get();
        List<Student> list03 = completableFuture03.get();
        long time02 = System.currentTimeMillis();
        System.out.println(list01.size() + list02.size() + list03.size());
        System.out.println(time02 - time01);
    }


    @Test
    void testFast() throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        long time01 = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(() -> {
            try {
                semaphore.acquire();
                StudentExample studentExample01 = new StudentExample();
                studentExample01.createCriteria().andCarLike("li");
                studentMapper.selectByExample(studentExample01);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
                semaphore.release();
            }

        }).start();

        new Thread(() -> {
            try {
                semaphore.acquire();
                StudentExample studentExample02 = new StudentExample();
                studentExample02.createCriteria().andNameLike("li");
                studentMapper.selectByExample(studentExample02);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
                semaphore.release();
            }

        }).start();

        new Thread(() -> {
            try {
                semaphore.acquire();
                StudentExample studentExample03 = new StudentExample();
                studentExample03.createCriteria().andAgeBetween(40000, 50000);
                studentMapper.selectByExample(studentExample03);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
                semaphore.release();
            }

        }).start();
        countDownLatch.await();
        long time02 = System.currentTimeMillis();
        System.out.println(time02 - time01);
    }

}
