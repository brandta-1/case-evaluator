package com.crates.value;

import java.util.Arrays;
import java.util.Objects;

import static com.crates.value.CsData.odds;

public class WearOdds {

    public static double[] getWearOdds(double[][] baseTable,double[][] newTable){


        double[] returnOdds = new double[baseTable.length];

        //creates a local copy of the odds
        double [] localOdds = odds.stream().mapToDouble(i->i).toArray();

        int k = 0;
        for( int i = 0; i < baseTable.length; i++){
            for( int j = k; j < newTable.length; j++){

                if(baseTable[i][1] >= newTable[j][1]){
                    returnOdds[i] += localOdds[j];
                    k++;
                } else if (baseTable[i][1] > newTable[j][0]){
                    double portion = ( ( baseTable[i][1] - newTable[j][0] ) / (newTable[j][1] - newTable[j][0]) );
                    returnOdds[i] += localOdds[j] * portion;
                    localOdds[j] = localOdds[j] * (1-portion);
                    newTable[j][0] = baseTable[i][1];
                } else {
                    break;
                }
            }
        }
        return returnOdds;
    }
}
