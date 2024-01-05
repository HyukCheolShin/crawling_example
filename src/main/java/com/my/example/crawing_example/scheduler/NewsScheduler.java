package com.my.example.crawing_example.scheduler;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.my.example.crawing_example.service.NewsService;

import jakarta.annotation.PostConstruct;

@Component
public class NewsScheduler {

    @Autowired
    private NewsService newsService;

    @PostConstruct
    public void start() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(NewsJob.class)
                .withIdentity("newsJob")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("newsTrigger")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInMinutes(1)
                        .repeatForever())
                .build();

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.getContext().put("newsService", newsService); // 서비스를 스케줄러 컨텍스트에 추가
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }

    public static class NewsJob implements Job {
        @Override
        public void execute(JobExecutionContext context) {
            try {
                NewsService newsService = (NewsService)context.getScheduler().getContext().get("newsService");
                System.out.println("Executing News Job");
            	String searchKeyword = "DB";
                String[] includeKeywords = {"DB"}; 
                String[] excludeKeywords = {"37만명"};
                int maxResults =  10;

                newsService.getFilteredNews(searchKeyword, includeKeywords, excludeKeywords, maxResults);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }
}