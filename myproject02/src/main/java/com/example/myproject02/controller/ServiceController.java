package com.example.myproject02.controller;

import com.example.myproject02.util.HttpClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

@Controller
public class ServiceController {

    @RequestMapping("/testHttp")
    @ResponseBody
    public String testHttp(){
        System.out.println("************");
        String s = HttpClientUtil.doGet("http://localhost:8080/hello");
        System.out.println(s);
        return s;
    }

    @RequestMapping("/downLoadFromUrl")
    public String downLoadFromUrl(){
        HttpClientUtil.download("http://localhost:8080/downLoad",new File("D:\\test.rar"));
        return "下载完成";
    }

}
