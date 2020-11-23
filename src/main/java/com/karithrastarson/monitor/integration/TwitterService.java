package com.karithrastarson.monitor.integration;

import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

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
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            twitter.updateStatus(tweet);

        } catch (TwitterException e) {
            writeTweetToFile(tweet);
            LOGGER.severe("Failed tweeting: \n" + tweet);
        }
        return true;
    }

    private void writeTweetToFile(String tweet) {
        try (FileWriter fileWriter = new FileWriter(twitterFile, true)) {
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(tweet);
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }
}
