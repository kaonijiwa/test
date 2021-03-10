package com.example.provider.service.impl;

import com.example.provider.mapper.StudentMapper;
import com.example.provider.pojo.Student;
import com.example.provider.pojo.StudentExample;
import com.example.provider.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> selectAllByName(String name) {
        long startTime = System.currentTimeMillis();
        FutureTask<List<Student>> task01 = new FutureTask<List<Student>>(() -> {
            StudentExample studentExample = new StudentExample();
            studentExample.createCriteria().andNameLike("%" + name + "%");
            studentExample.setOrderByClause("id desc");
            return studentMapper.selectByExample(studentExample);
        });

        new Thread(task01).start();
        FutureTask<List<Student>> task02 = new FutureTask<List<Student>>(() -> {
            StudentExample example = new StudentExample();
            example.createCriteria().andCarLike("%" + name + "%");
            example.setOrderByClause("name desc");
            return studentMapper.selectByExample(example);
        });
        new Thread(task02).start();
        List<Student> list01 = null;
        List<Student> list02 = null;
        try {
            /***
             * get方法：获取计算结果（如果还没计算完，也是必须等待的）
             * cancel方法：还没计算完，可以取消计算过程
             * isDone方法：判断是否计算完
             * isCancelled方法：判断计算是否被取消
             * 这些接口的设计很完美，FutureTask的实现注定不会简单，后面再说。
             */
            list01 = task01.get();
            list02 = task02.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("耗时："+(System.currentTimeMillis()-startTime));
        return list01;
    }

    @Override
    public List<Student> simpleSelectAllByName(String name) {
        long startTime = System.currentTimeMillis();
        StudentExample studentExample = new StudentExample();
        studentExample.createCriteria().andNameLike("%" + name + "%");
        studentExample.setOrderByClause("id desc");
        List<Student> list01 = studentMapper.selectByExample(studentExample);
        StudentExample example = new StudentExample();
        example.createCriteria().andCarLike("%" + name + "%");
        example.setOrderByClause("name desc");
        List<Student> list02 = studentMapper.selectByExample(example);
        System.out.println("耗时："+(System.currentTimeMillis()-startTime));
        return list01;
    }

}
