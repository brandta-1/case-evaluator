package com.crates.value;

public class PartitionWear {

    public PartitionWear(){

    }

    // given an items floatcaps, partition them over the possible float values and return their weighted probabilities
    public int getFloatGaps(double[] floatCaps){

        double[] gaps = {0.07,0.15,0.38,0.45};

        boolean lowerFound = false;
        int gapsCount = 0;
        for(int i =0; i<4; i++){

            //if the lower floatCap is outside a gap
           if(gaps[i] > floatCaps[0]){
               lowerFound = true;
           }

           // then check if the upper floatCap is outside a gap
           if(lowerFound){
               if(gaps[i] < floatCaps[1]){
                   gapsCount++;
               } else {
                   break;
               }
           }
        }

        return gapsCount;
    };



}
