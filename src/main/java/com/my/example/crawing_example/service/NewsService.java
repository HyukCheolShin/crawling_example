package com.my.example.crawing_example.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.my.example.crawing_example.model.NewsDto;

@Service
public class NewsService {
    public List<NewsDto> getFilteredNews(String searchKeyword, String[] includeKeywords, String[] excludeKeywords, int maxResults) {
        List<NewsDto> newsList = new ArrayList<>();
        String url = "https://search.naver.com/search.naver?where=news&query=" + searchKeyword;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements newsHeadlines = doc.select(".news_tit");
            int count = 0;
            for (Element headline : newsHeadlines) {
                if (count >= maxResults) break;

                String title = headline.text();
                String link = headline.attr("href");
                if (isTitleValid(title, includeKeywords, excludeKeywords)) {
                	System.out.println("title : " + title + ", link : " + link);
                    newsList.add(new NewsDto(title, link));
                    count++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    private boolean isTitleValid(String title, String[] includeKeywords, String[] excludeKeywords) {
        if (includeKeywords != null) {
            for (String keyword : includeKeywords) {
                if (!title.contains(keyword)) return false;
            }
        }
        if (excludeKeywords != null) {
            for (String keyword : excludeKeywords) {
                if (title.contains(keyword)) return false;
            }
        }
        return true;
    }

}
