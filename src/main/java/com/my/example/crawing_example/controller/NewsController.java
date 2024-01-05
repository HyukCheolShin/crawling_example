package com.my.example.crawing_example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.example.crawing_example.model.NewsDto;
import com.my.example.crawing_example.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/call-test")
    public String getCallTest() {
    	System.out.println("/api/news/call-test 호출에 성공하였습니다.");
    	return "Success";
    }

    @GetMapping("/naver/search")
    public List<NewsDto> getNews() {
    	String searchKeyword = "DB";
        String[] includeKeywords = {"DB"}; 
        String[] excludeKeywords = {"37만명"};
        int maxResults =  10;

        return newsService.getFilteredNews(searchKeyword, includeKeywords, excludeKeywords, maxResults);
    }
}