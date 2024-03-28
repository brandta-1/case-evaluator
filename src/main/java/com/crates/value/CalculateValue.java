package com.crates.value;

public class CalculateValue {

    public static double calculateRewardValue(double[] wearOdds, String[] prices, double statTrakOdds){

        double returnValue = 0.0;
        for(int i = 0; i<prices.length; i++){
            //it could be 'Not Possible' or 'No Recent Price'
            if(prices[i].charAt(0) != '$'){
                continue;
            }

            double currentPrice = Double.parseDouble(prices[i].substring(1));

            if(i < prices.length/2){
                returnValue += ( wearOdds[i] * (statTrakOdds * currentPrice) );
            } else {
                returnValue += ( wearOdds[i - ( prices.length / 2 )] * ( ( 1 - statTrakOdds ) *currentPrice) );
            }
        }
        return returnValue;
    }
}
