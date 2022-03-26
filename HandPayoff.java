//
// HandPayoff.java
// Contain definitions and methods for the HandPayoff class needed to initialize the data fields
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

public class HandPayoff {
    double[][] HandPayoff;
    public HandPayoff(String file)throws FileNotFoundException{
        HandPayoff = new double[1326][1326];

        
        // Parses through the csv file to store into hand equities matrix
        Scanner sc = new Scanner(new File(file));
        sc.useDelimiter(",|\r\n");
        String strEquity;

        for(int i = 0; i<1326; i++){
            for(int j =0; j<1326; j++){
                    strEquity = sc.next();
                    HandPayoff[i][j] = Double.valueOf(strEquity.replaceAll("[^\\d-.E]","")).doubleValue();
/** 
                    System.out.print(i+" ");
                    System.out.print(j);
                    System.out.print('\n');
     **/
                 
                }
        }
    }


/**
 getEquity

 @param h1 hand1
 @param h2 hand2
 @return double returns the z(h1,h2) value
*/  

    public double getPayoff(int h1, int h2){
        return HandPayoff[h1][h2];
    }
}
