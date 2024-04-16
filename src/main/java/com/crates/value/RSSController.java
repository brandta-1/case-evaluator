package com.crates.value;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/rss")
public class RSSController {

    @GetMapping
    public Iterable<Article> testing() {
        List<Article> articles = new ArrayList<>();
        try {
            getFeedItems(articles);
        } catch (MalformedURLException ue){
            System.out.println("URL error");
        } catch (IOException ioe) {
            System.out.println("Reading error");
        }
        System.out.println(articles);
        return articles;
    }

    private static void getFeedItems(List<Article> articles) throws IOException {
        URL rssURL = new URL("https://www.hltv.org/rss/news");
        BufferedReader in = new BufferedReader(new InputStreamReader(rssURL.openStream()));
        String line;
        List<String> titles = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        List<String> links = new ArrayList<>();
        List<String> images = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        boolean append = false;
        Pattern linkPattern = Pattern.compile("\"(.*?)\"");

        //check each line in the xml, once an <item> tag is hit,
        // take each child element and parse its tag and text,
        // then set its text to the appropriate Article attribute based on the tag
        int index = 0;
        while((line=in.readLine())!=null) {

            //don't need all the articles so just stop at 4
            if(index==4){
                break;
            }
            //stop appending if the <item> element has closed
            if("</item>".equals(line.replace(" ",""))){
                append = false;
                indexes.add(index);
                index++;
            }

            //if we are inside an <item> element
            if(append){

                //split at tag closing to get tag type and text
                String[] element = line.split(">");

                //if for some reason the line is more than a tag and text, continue
                if(element.length < 2 ){
                    continue;
                }

                //if it's a media tag, extract the url from its attributes
                if(element[0].contains("media")){
                    Matcher m = linkPattern.matcher(element[0]);
                    if(m.find()){
                        String image =m.group(1).replace("amp;","");
                        images.add(image);
                    }
                }
                    //remove indentations and closing tags
                    String tag = element[0].replace(" ","").substring(1);
                    String text = element[1].split("<")[0];

                    //add the text to its corresponding attribute array
                    switch(tag){
                        case "title":
                            titles.add(text);
                            break;
                        case "description":
                            descriptions.add(text);
                            break;
                        case "link":
                            links.add(text);
                            break;
                        default:
                            break;
                    }
            }

            //start appending if we reach an <item> element
            if("<item>".equals(line.replace(" ",""))){
                append = true;
            }
        }

        //for all the attributes we have, generate Articles
        for( int i = 0; i<titles.size(); i++){
            Article article = new Article(titles.get(i), descriptions.get(i), links.get(i), images.get(i), indexes.get(i));
            articles.add(article);
        }
    }
}
