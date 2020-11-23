package com.karithrastarson.monitor.service;

import com.karithrastarson.monitor.entity.NewsItem;
import com.karithrastarson.monitor.integration.TwitterService;
import com.karithrastarson.monitor.integration.VisirService;
import com.karithrastarson.monitor.repository.NewsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
                    //Does one of the already registered have the same headline?
                    Optional<NewsItem> match = results.stream()
                            .filter(oldEntry -> entry.getValue().equals(oldEntry.getHeadline()))
                            .findFirst();

                    if (match.isEmpty()) {
                        //If no match, then
                        results.forEach(oldEntry -> {
                            if (!entry.getValue().equals(oldEntry.getHeadline())) {
                                Date date = new Date();
                                DateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                                String stringDate = sdf.format(date);

                                String tweet = stringDate + " - Fyrirsögn breyttist fyrir eftirfarandi frétt: " + oldEntry.getUrl() + " \n ";
                                tweet = tweet.concat("Fyrirsögn áður: " + oldEntry.getHeadline() + "\n");
                                tweet = tweet.concat("Fyrirsögn nú: " + entry.getValue());
                                twitterService.doTweet(tweet);

                                //Save updated item
                                NewsItem newsItem = new NewsItem(url, entry.getValue());
                                newsItemRepository.save(newsItem);
                            }
                        });
                    }
                }
            }
        }
        return count;
    }
}
