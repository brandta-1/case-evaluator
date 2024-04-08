package com.crates.value;

public class CalculateValue {

    public static double calculateRewardValue(double[] wearOdds, String[] prices, double statTrakOdds){

        double returnValue = 0.0;

        for(int i = 0; i< prices.length / 2; i++){
            //it could be 'Not Possible' or 'No Recent Price'
            if(prices[i].equals("Not Possible")){
                continue;
            }

            double statTrakPrice = Double.parseDouble(prices[i]);
            double normalPrice = Double.parseDouble(prices[i+ (prices.length/2)]);
            returnValue +=  wearOdds[i] * ( (statTrakOdds * statTrakPrice) + ( (1-statTrakOdds) * normalPrice) );
        }
        return returnValue;
    }
}
