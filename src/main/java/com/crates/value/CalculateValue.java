package com.crates.value;

public class CalculateValue {

    public static double calculateRewardValue(double[] wearOdds, String[] prices, double statTrakOdds){

        double returnValue = 0.0;

        for(int i = 0; i< prices.length / 2; i++){
            //it could be 'Not Possible' or 'No Recent Price'
            if(prices[i].equals("Not Possible")){
                continue;
            }

            if(prices[i].equals("No Recent Price")){
                prices[i] = "0.0";
            }

            if(prices[i + (prices.length/2)].equals("No Recent Price")){
                prices[i + (prices.length/2)] = "0.0";
            }

            double statTrakPrice = Double.parseDouble(prices[i].substring(1).replace(",",""));
            double normalPrice = Double.parseDouble(prices[i+ (prices.length/2)].substring(1).replace(",",""));
            returnValue +=  wearOdds[i] * ( (statTrakOdds * statTrakPrice) + ( (1-statTrakOdds) * normalPrice) );

        }
        return returnValue;
    }
}
