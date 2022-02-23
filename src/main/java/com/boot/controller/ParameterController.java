/*
 * Copyright 2021 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.boot.controller;

import com.boot.domain.Person;
import com.boot.domain.Pet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title 阳光正好，微风不燥
 * @data 2021/11/25
 */
@Controller
//@RestController
public class ParameterController {
    @RequestMapping("/{id}/{username}")
    public Map<String,Object> test_1(@PathVariable("id") int id, @PathVariable("username") String username,
                                     @PathVariable Map<String,String> paraMap,
                                     @RequestHeader("User-Agent") String userAgent,
                                     @RequestHeader Map<String,String> header){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("username",username);
        map.put("parameters",paraMap);
        map.put("User-Agent",userAgent);
        map.put("header",header);
        return map;
    }

    @PostMapping("/value")
    public Map<String,Object> test_2(@RequestBody String content){
        Map<String,Object> map = new HashMap<>();
        map.put("body",content);
        return map;
    }

    @GetMapping("/user/{user}")
    public Map<String,Object> test_3(@PathVariable("user") String path,
                                     @MatrixVariable("id") Integer id,
                                     @MatrixVariable("username") List<String> username){
        Map<String,Object> map = new HashMap<>();
        map.put("path",path);
        map.put("id",id);
        map.put("userList",username);
        return map;
    }

    @GetMapping("/params")
    public String test_params(Map<String,Object> map,
                              Model model,
                              HttpServletRequest request,
                              HttpServletResponse response){
        map.put("map","map_message");
        model.addAttribute("model","model_message");
        request.setAttribute("request","req_message");
        Cookie cookie = new Cookie("c1","v1");

        response.addCookie(cookie);
        return "forward:/success";

    }
    @ResponseBody
    @GetMapping("/success")
    public Map<String,Object> test_success(@RequestAttribute("request")String req_msg,
                                           HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        map.put("request",req_msg);
        map.put("map_msg",request.getAttribute("map"));
        map.put("model_msg",request.getAttribute("model"));

        return map;
    }

    @PostMapping("/saveUser")
    public Person save_user(Person person){
        return  person;
    }

    @ResponseBody
    @GetMapping("/test/person")
    public Person test_json(){
        Person person = new Person();
        person.setAge(28);
        person.setBirth(new Date());
        person.setUserName("test_name");
        person.setPet(new Pet());
        return person;
    }

}