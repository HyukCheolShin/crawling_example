package com.my.example.crawing_example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @GetMapping("/call-test")
    public String getCallTest() {
    	System.out.println("/api/news/call-test 호출에 성공하였습니다.");
    	return "Success";
    }
}