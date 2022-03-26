
//
// strategies.java
// Contain definitions and methods for the strategies class needed to initialize the data fields
//
// Created by Caitlyn Liu on 2021-01-26
// Copyright ©2021 CaitlynLiu. All rights reserved.

import java.io.*;
import java.util.Scanner;

/**
 Strategies

 The constructor for the Strategies class. Initializes the data fields and structure of Strategies class.

 @throws an FileNotFoundException if the data provided is invalid
*/

public class Strategies {
    double[] frequencies;
    double[] strategy;
    public Strategies(String candidate) throws FileNotFoundException{
        strategy = new double[1326];
        frequencies = new double[1326];
        Scanner sc = new Scanner(new File(candidate));
        sc.useDelimiter(",");
        double stratVal;
        for(int i =0; i<1326; i++){
            // Store the data from strategies file into frequencies
            stratVal = Double.valueOf(sc.next().replaceAll("[^0-9]","")).doubleValue();
            strategy[i] = stratVal;
            frequencies[i] = stratVal;
            }
    }


/**
 getStrategy

 @param int index
 @return % times hand should be played 
*/  
public double getStrategy(int index){
    return strategy[index];
}

/**
updateFrequency

@param int index
@param double strategy

Updates frequency stored in index to new frequency

*/  

public void updateStrategy(int index, double strat){
    strategy[index] = strat;

}

/**
 getFrequency

 @param double index
 @return frequency of playing that hand
*/  
    public double getFrequency(int index){
        return frequencies[index];
    }

/**
 updateFrequency

 @param int index
 @param double frequency

 Updates frequency stored in index to new frequency

*/  

    public void updateFrequency(int index, double frequency){
        frequencies[index] = frequency;

    }
}
