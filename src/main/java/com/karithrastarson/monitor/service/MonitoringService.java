package com.karithrastarson.monitor.service;

import com.karithrastarson.monitor.entity.NewsItem;
import com.karithrastarson.monitor.integration.TwitterService;
import com.karithrastarson.monitor.integration.VisirService;
import com.karithrastarson.monitor.repository.NewsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class MonitoringService {
    private static final Logger LOGGER = Logger.getLogger(MonitoringService.class.getName());

    @Autowired
    NewsItemRepository newsItemRepository;

    @Autowired
    TwitterService twitterService;

    public int refresh() {
        int count = 0;
        VisirService visirService = new VisirService();
        Map<String, String> headlines = visirService.getHeadlines();
        if (!headlines.isEmpty()) {
            for (Map.Entry<String, String> entry : headlines.entrySet()) {
                String url = visirService.createUrl(entry.getKey());
                List<NewsItem> results = newsItemRepository.findByUrl(url);

                //If empty, then create in database
                if (results.isEmpty()) {
                    count++;
                    NewsItem newsItem = new NewsItem(url, entry.getValue());
                    newsItemRepository.save(newsItem);
                } else {
                    NewsItem oldEntry = results.get(results.size() - 1);
                    if (!entry.getValue().equals(oldEntry.getHeadline())) {
                        String tweet = "Headline change for url " + entry.getKey() + ". \n ";
                        tweet = tweet.concat("Previous headline: " + oldEntry.getHeadline() + "\n");
                        tweet = tweet.concat("New headline: " + entry.getValue());
                        twitterService.doTweet(tweet);

                        //Save updated item
                        NewsItem newsItem = new NewsItem(url, entry.getValue());
                        newsItemRepository.save(newsItem);
                    }
                }
            }
        }
        return count;
    }
}
