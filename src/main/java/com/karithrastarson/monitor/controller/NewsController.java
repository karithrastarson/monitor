package com.karithrastarson.monitor.controller;

import com.karithrastarson.monitor.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/news")
public class NewsController {
    private static final Logger LOGGER = Logger.getLogger(NewsController.class.getName());

    @Autowired
    MonitoringService monitoringService;

    @PostMapping(path = "/refresh")
    public @ResponseBody
    ResponseEntity<String> start() {
        int count = monitoringService.refresh();
        return new ResponseEntity<>(count + " new headlines added to the database", HttpStatus.OK);
    }

}
