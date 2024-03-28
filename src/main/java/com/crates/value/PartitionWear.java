package com.crates.value;

public class PartitionWear {

    //you don't need the default constructor, none of these classes have their own dependencies, ie their own classes, if you were to instantiate that outside the autowired constructor, then it would be
    //you would have to recursively instantiate dependencies, that's why doing it inside the autowired constructor is necessary

    //inside main, you would have a seperate package, a configuration package, where you can configure dependencies, with a bean property class
    public PartitionWear(){

    }

    //move it out of the class
    // given an items floatcaps, partition them over the possible float values and return their weighted probabilities
    public int getFloatGaps(double[][] boundaries, double[] floatCaps){

        double[] gaps = new double[boundaries.length-1];

        for(int i = 0; i < boundaries.length-1; i++){
            gaps[i] = boundaries[i][1];
        }

        boolean lowerFound = false;
        int gapsCount = 0;
        for (double gap : gaps) {

            //if the lower floatCap is outside a gap
            if (gap > floatCaps[0]) {
                lowerFound = true;
            }

            // then check if the upper floatCap is outside a gap
            if (lowerFound) {
                if (gap < floatCaps[1]) {
                    gapsCount++;
                } else {
                    break;
                }
            }
        }

        return gapsCount;
    };



}
