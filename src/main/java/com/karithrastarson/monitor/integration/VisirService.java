package com.karithrastarson.monitor.integration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class VisirService {
    private static final Logger LOGGER = Logger.getLogger(VisirService.class.getName());

    private final String sourceURL = "https://visir.is";

    public VisirService() {
    }

    public Map<String, String> getHeadlines() {
        Document doc;
        Map<String, String> headlines = new HashMap<>();
        try {
            doc = Jsoup.connect(sourceURL).get();

            Elements headlineElements = doc.select("article.article-item.article-item--simple:not(.-more) > h1 > a");

            for (Element headlineElement : headlineElements) {
                String headline = headlineElement.text();
                String id = headlineElement.attr("href").split("/")[2];
                if (!id.contains("-") && !containsSpecialCharacter(headline)) {
                    headlines.put(id, headline);
                }
            }
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
        return headlines;
    }

    private boolean containsSpecialCharacter(String headline) {
        if (headline.contains("ż")) return true;
        if (headline.contains("ń")) return true;
        if (headline.contains("ą")) return true;

        return false;
    }

    public String createUrl(String key) {
        return sourceURL + "/g/" + key;
    }
}
