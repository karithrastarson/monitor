package com.karithrastarson.monitor.service;

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

    public int doTweet(String tweet) {
        writeTweetToFile(tweet);
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            twitter.updateStatus(tweet);
            return 1;
        } catch (TwitterException e) {
            LOGGER.severe("Failed tweeting: \n" + tweet);
            return 0;
        }
    }

    public int tweetHeadlineChange(String oldHeadline, String newHeadline, String url) {
        String tweet = "\u270F Fyrirsögn breyttist fyrir eftirfarandi frétt: " + url + " \n\n";
        tweet = tweet.concat("\u274C Fyrirsögn áður: " + oldHeadline + "\n\n");
        tweet = tweet.concat("\u2705 Fyrirsögn nú: " + newHeadline);

        if (filterTweets(oldHeadline)) {
            return doTweet(tweet);
        }
        return 0;
    }

    private boolean filterTweets(String oldHeadline) {
        if (oldHeadline.contains("beinni"))
            return false;
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
