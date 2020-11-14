package com.karithrastarson.monitor.service;

import com.karithrastarson.monitor.entity.NewsItem;
import com.karithrastarson.monitor.integration.Visir;
import com.karithrastarson.monitor.repository.NewsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class MonitoringService {
    private static final Logger LOGGER = Logger.getLogger(MonitoringService.class.getName());

    @Autowired
    NewsItemRepository newsItemRepository;

    public int refresh() {
        int count = 0;
        Visir visir = new Visir();
        Map<String, String> headlines = visir.getHeadlines();
        if (!headlines.isEmpty()) {
            for (Map.Entry<String, String> entry : headlines.entrySet()) {
                String url = visir.createUrl(entry.getKey());
                Optional<NewsItem> optional = newsItemRepository.findByUrl(url);

                if (optional.isEmpty()) {
                    count++;
                    NewsItem newsItem = new NewsItem(url, entry.getValue());
                    newsItemRepository.save(newsItem);
                }
            }
        }
        return count;
    }
}
