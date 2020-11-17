package com.karithrastarson.monitor.integration;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@Component
public class TwitterService {
    private static final Logger LOGGER = Logger.getLogger(TwitterService.class.getName());

    private final String twitterFile = "tweets.txt";

    public TwitterService() {
    }

    public boolean doTweet(String tweet) {
        try (FileWriter fileWriter = new FileWriter(twitterFile, true)) {
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(tweet);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
        return true;
    }
}
