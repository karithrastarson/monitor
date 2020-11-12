package com.karithrastarson.monitor.repository;

import com.karithrastarson.monitor.entity.NewsItem;
import org.springframework.data.repository.CrudRepository;

public interface NewsItemRepository extends CrudRepository<NewsItem, Long> {
}
