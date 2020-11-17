package com.karithrastarson.monitor.repository;

import com.karithrastarson.monitor.entity.NewsItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsItemRepository extends CrudRepository<NewsItem, Long> {

    List<NewsItem> findByUrl(String url);

}
