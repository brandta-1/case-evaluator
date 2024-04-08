package com.crates.value;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
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
    public static Double getContainerPrice(String containerURL) throws IOException {

        Document doc = Jsoup.connect(containerURL).get();

        //TODO error-handling if this is not found
        Element link = doc.select("a.market-button-item").get(1);

        String linkText = link.text();
        String priceString = linkText.split(" ")[0];

        Prevent429();
        return Double.parseDouble(priceString.substring(1));
    }

    //scrapes for a single reward item
    public static String[] getRewardPrice(String rewardURL) throws IOException {
        Document doc = Jsoup.connect(rewardURL).get();
        Element left = doc.select("div.price-details").getFirst();
        //System.out.println(left);
        //this pulls all prices for steam AND for bit skins, so we ignore the 2nd half
        Elements prices = left.select("span.pull-right");
        String[] returnPrices = new String[prices.size() / 2];

        for(int i = 0; i < prices.size() / 2; i++) {
            String currentPrice = prices.get(i).text();
            if(currentPrice.equals("No Recent Price")){
                returnPrices[i] = "0.0";
            } else if(currentPrice.equals("Not Possible")){
                returnPrices[i] = currentPrice;
            } else {
                returnPrices[i] = currentPrice.substring(1).replace(",","");
            }
        }

        Prevent429();
        return returnPrices;
    }
}