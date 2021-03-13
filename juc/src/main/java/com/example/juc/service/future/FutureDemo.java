package com.example.juc.service.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureDemo demo = new FutureDemo();
        demo.supplyAsync();
    }

    //没有返回值的异步回掉
    public void runAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "runAsync=>void");
        });
        System.out.println("11111111111");
        //获取执行结果
        future.get();
    }


    public void supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            int i = 0 / 0;
            System.out.println(Thread.currentThread().getName() + "supplyAsync=>Integer");
            return 1024;
        });
        Integer index = completableFuture.whenComplete((t, u) -> {
            //t是正常的返回结果
            System.out.println(t);
            //u是错误的信息
            System.out.println(u);
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return 233;
        }).get();
        System.out.println("结果：" + index);
    }

}
