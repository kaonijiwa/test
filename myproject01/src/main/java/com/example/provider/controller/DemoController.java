package com.example.provider.controller;

import com.example.provider.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    @Autowired
    private DemoService demoService;
    @RequestMapping("/hello/{name}")
    @ResponseBody
    public Object hello(@PathVariable String name){
        return demoService.selectAllByName(name);
    }

    @RequestMapping("/hello2/{name}")
    @ResponseBody
    public Object hello2(@PathVariable String name){
        return demoService.simpleSelectAllByName(name);
    }

}
