package com.karithrastarson.monitor.service;

import com.karithrastarson.monitor.entity.NewsItem;
import com.karithrastarson.monitor.repository.NewsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class WebPageService {

    @Autowired
    NewsItemRepository newsItemRepository;

    @Autowired
    TwitterService twitterService;

    protected String cleanUnicode(String headline) {
        return headline.replaceAll("\u00AD", "");
    }

    protected boolean containsSpecialCharacter(String headline) {
        if (headline.contains("ż")) return true;
        if (headline.contains("ń")) return true;
        if (headline.contains("ą")) return true;
        if (headline.contains("ś")) return true;
        if (headline.contains("ł")) return true;


        return false;
    }

    public int refresh() {
        int tweetCount = 0;
        Map<String, String> headlines = getHeadlines();

        if (!headlines.isEmpty()) {
            for (Map.Entry<String, String> entry : headlines.entrySet()) {
                String url = createUrl(entry.getKey());
                List<NewsItem> results = newsItemRepository.findByUrl(url);

                //If empty, then create in database
                if (results.isEmpty()) {

                    NewsItem newsItem = new NewsItem(url, entry.getValue());
                    newsItemRepository.save(newsItem);
                } else {
                    //Does one of the already registered have the same headline?
                    Optional<NewsItem> match = results.stream()
                            .filter(oldEntry -> entry.getValue().equals(oldEntry.getHeadline()))
                            .findFirst();

                    if (match.isEmpty()) {
                        //If no match, then
                        for (NewsItem oldEntry : results) {
                            if (!entry.getValue().equals(oldEntry.getHeadline())) {
                                tweetCount += twitterService.tweetHeadlineChange(oldEntry.getHeadline(), entry.getValue(), oldEntry.getUrl());
                                //Save updated item
                                NewsItem newsItem = new NewsItem(url, entry.getValue());
                                newsItemRepository.save(newsItem);
                            }
                        }
                    }
                }
            }
        }
        return tweetCount;
    }

    public abstract Map<String, String> getHeadlines();

    public abstract String createUrl(String key);
}
