package com.karithrastarson.monitor.controller;

import com.karithrastarson.monitor.service.DVService;
import com.karithrastarson.monitor.service.VisirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/monitor")
public class MonitorController {

    @Autowired
    VisirService visirService;

    @Autowired
    DVService dvService;

    @PostMapping(path = "/visir/refresh")
    public @ResponseBody
    ResponseEntity<String> refreshVisir() {
        int count = visirService.refresh();
        return new ResponseEntity<>(count + " tweets sent out", HttpStatus.OK);
    }

    @PostMapping(path = "/dv/refresh")
    public @ResponseBody
    ResponseEntity<String> refreshDV() {
        int count = dvService.refresh();
        return new ResponseEntity<>(count + " tweets sent out", HttpStatus.OK);
    }

    @PostMapping(path = "/refresh")
    public @ResponseBody
    ResponseEntity<String> refreshAll() {
        int count = 0;
        count += dvService.refresh();
        count += visirService.refresh();

        return new ResponseEntity<>(count + " tweets sent out", HttpStatus.OK);
    }

}
