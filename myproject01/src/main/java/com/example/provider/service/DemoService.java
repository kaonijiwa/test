package com.example.provider.service;

import com.example.provider.pojo.Student;

import java.util.List;

public interface DemoService {


    List<Student> selectAllByName(String name);


    List<Student> simpleSelectAllByName(String name);

}
