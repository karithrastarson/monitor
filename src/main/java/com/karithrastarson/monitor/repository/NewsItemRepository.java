package com.karithrastarson.monitor.repository;

import com.karithrastarson.monitor.entity.NewsItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NewsItemRepository extends CrudRepository<NewsItem, Long> {

    Optional<NewsItem> findByUrl(String url);

}
