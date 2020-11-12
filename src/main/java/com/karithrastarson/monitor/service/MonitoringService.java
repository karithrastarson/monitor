package com.karithrastarson.monitor.service;

import com.karithrastarson.monitor.repository.NewsItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {

    @Autowired
    NewsItemRepository newsItemRepository;

    public void refresh() {

    }
}
