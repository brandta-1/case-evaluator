package com.crates.value;

public class WearOdds {
    public static double[] getWearOdds(double[][] boundaries, double[] floatCaps, Integer floatGaps){

        double[] returnOdds = {0.07,0.07,0.21,0.06,0.55};

        //can be used with a single iterator because floatCaps[0] is always less than floatCaps[1]
        for( int i=0; i<5; i++){
            if(floatCaps[0] < boundaries[i][1]){
                returnOdds[i] = returnOdds[i] - (floatCaps[0] - boundaries[i][0]);
            } else {
                returnOdds[i] = 0;
            }

            if(floatCaps[1] > boundaries[4-i][0]) {
                returnOdds[4 - i] = returnOdds[4 - i] - (boundaries[4 - i][1] - floatCaps[1]);
            } else {
                returnOdds[i-4] = 0;
            }
        }

        //but we have to iterate twice to denominate, so the float-capped spaces are proportional ie sum to 1.00
        for( int i=0; i<5; i++){
            if(returnOdds[i] != 0){
                returnOdds[i] = returnOdds[i] / ( (floatCaps[1] - floatCaps[0]) - (0.01 * floatGaps) );
            }
        }

        return returnOdds;
    }
}
