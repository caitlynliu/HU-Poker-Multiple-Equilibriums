//
// HandEquities.java
// Contain definitions and methods for the HandEquities class needed to initialize the data fields
//
// Created by Caitlyn Liu on 2021-01-26
// Copyright ©2021 CaitlynLiu. All rights reserved.

import java.io.*;
import java.util.Scanner;

/**
 HandEquities

 The constructor for the HandEquities class. Initializes the data fields HandEquities class.

 @throws an FileNotFoundException if the data provided is invalid
*/

public class HandEquities {
    float[][] HandEquities;
    public HandEquities(String equities)throws FileNotFoundException{
        HandEquities = new float[1326][1326];
        Scanner sc = new Scanner(new File(equities));
        sc.useDelimiter(",|\r\n");

        // Parses through the csv file to store into hand equities matrix
        for(int i = 0; i<1326; i++){
            for(int j =0; j<1326; j++){
                    String strEquity = sc.next();
                    HandEquities[i][j] = Float.valueOf(strEquity.replaceAll("[^0-9.]","").substring(0,Math.min(strEquity.length(),5))).floatValue();
                    System.out.print(i+" ");
                    System.out.print(j);
                    System.out.print('\n');
                }
        }
    }

/**
 getEquity

 @param h1 hand1
 @param h2 hand2
 @return double returns the probability of winning at showdown between two hands
*/  

    public double getEquity(int h1, int h2){
        return HandEquities[h1][h2];
    }
}
