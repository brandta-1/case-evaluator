package com.crates.value;

public class TransformTable {
    public static double[][] transformTable(double[][] baseTable, double[] floatCaps){


        double[][] newTable = new double[baseTable.length][2];

        for( int i =0; i< baseTable.length; i++){
            for( int j = 0; j< baseTable[i].length; j++){
                newTable[i][j] = ( baseTable[i][j] * (floatCaps[1] - floatCaps[0]) ) + floatCaps[0];
            }
        }

        return newTable;
    }
}
