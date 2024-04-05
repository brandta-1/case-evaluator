package com.crates.value;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;
public class RetrieveWebData {

    private static void Prevent429(){
        try
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
    //scrapes for a case
    public static BigDecimal getContainerPrice(String containerURL) throws IOException {

        Document doc = Jsoup.connect(containerURL).get();

        //TODO error-handling if this is not found
        Element link = doc.select("a.market-button-item").get(1);

        String linkText = link.text();
        String priceString = linkText.split(" ")[0];

        Prevent429();
        return new BigDecimal(priceString.substring(1));
    }

    //scrapes for a single reward item
    public static String[] getRewardPrice(String rewardURL) throws IOException {

        Document doc = Jsoup.connect(rewardURL).get();

        Element left = doc.select("div.price-details").getFirst();

        //System.out.println(left);

        String[] returnPrices = new String[10];
        Elements prices = left.select("span.pull-right");

        for(int i = 0; i < 10; i++) {
            returnPrices[i] = prices.get(i).text();
        }

        Prevent429();
        return returnPrices;
    }
}