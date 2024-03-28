package com.crates.value;

public class WearOdds {
    public static double[] getWearOdds(double[][] boundaries, double[] floatCaps, Integer floatGaps){

        //init return array
        double[] returnOdds = new double[boundaries.length];

        for(int i=0; i< boundaries.length; i++){
            returnOdds[i] = boundaries[i][1] - boundaries[i][0];
        }

        //can use a single iterator because floatCaps[0] is always less than floatCaps[1]
        int end = returnOdds.length-1;
        for( int i=0; i<returnOdds.length; i++){
            if(floatCaps[0] < boundaries[i][1]){
                returnOdds[i] = returnOdds[i] - (floatCaps[0] - boundaries[i][0]);
            } else {
                returnOdds[i] = 0;
            }

            if(floatCaps[1] > boundaries[end-i][0]) {
                returnOdds[end - i] = returnOdds[end - i] - (boundaries[end - i][1] - floatCaps[1]);
            } else {
                returnOdds[i-end] = 0;
            }
        }

        //but we have to iterate twice to denominate, so the float-capped odds are proportional ie sum to 1.00
        for( int i=0; i<returnOdds.length; i++){
            if(returnOdds[i] != 0){
                returnOdds[i] = returnOdds[i] / ( (floatCaps[1] - floatCaps[0]) - (0.01 * floatGaps) );
            }
        }

        return returnOdds;
    }
}
