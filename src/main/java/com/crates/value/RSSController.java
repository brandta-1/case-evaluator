package com.crates.value;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


@RestController
@RequestMapping(value= "/rss" ,produces= MediaType.APPLICATION_XML_VALUE)
public class RSSController {

    @GetMapping
    public String testing() {
        String feedItems = "";
        try {
            feedItems = getFeedItems();
        } catch (MalformedURLException ue){
            System.out.println("URL error");
        } catch (IOException ioe) {
            System.out.println("Reading error");
        }
        return feedItems;
    }

    private static String getFeedItems() throws IOException {
        URL rssURL = new URL("https://www.hltv.org/rss/news");
        BufferedReader in = new BufferedReader(new InputStreamReader(rssURL.openStream()));
        String line;
        StringBuilder feed = new StringBuilder();
        while((line=in.readLine())!=null){
            feed.append(line);
        }
        return feed.toString();
    }
}
