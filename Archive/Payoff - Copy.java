//
// Payoff.java
// Contain definitions and methods for the Payoff class needed to initialize the data fields
//
// Created by Caitlyn Liu on 2021-01-26
// Copyright ©2021 CaitlynLiu. All rights reser

import java.io.*;

/**
 Payoff

 The constructor for the HandEquities class. Initializes the data fields Payoff class.

 @throws an FileNotFoundException if the data provided is invalid
*/
public class Payoff {
    HandEquities hvh;
    Converter converter;
    static double[][] handPayoffs;
    double handProb;
    public Payoff() throws FileNotFoundException, IOException{
        hvh = new HandEquities("hvh.csv");
        converter = new Converter("converter.txt");
        handPayoffs = new double[1326][1326];
        
        // Store index values for hole cards
        double stack = 9.5;
        for(int i=0; i<1326; i++){
            for(int j = 0; j<1326; j++){
                if(checkOverlap(i,j) == 0) {
                    handProb = 0.00000;
                }
                else{
                    handProb = 1.00000/1225;
                }
                double equity = hvh.getEquity(i, j)/100;
                handPayoffs[i][j]=handProb*(stack*(2*equity-1)-1);
            }
        }
    }



/**
 checkOverlap

 @param h1 int hand1
 @param h2 int hand2
 @return int returns 1 not overlap, returns 0 if overlap
*/  
public int checkOverlap(int h1, int h2){
    int overlap = 1;

    // converts integer to hands (strings)
    String hand1 = converter.getHand(h1);
    String hand2 = converter.getHand(h2);

    // Break holeCard hands into individual hands
    String h1c1 = new StringBuilder().append(hand1.charAt(0)).append(hand1.charAt(1)).toString();
    String h1c2 = new StringBuilder().append(hand1.charAt(2)).append(hand1.charAt(3)).toString();
    String h2c1 = new StringBuilder().append(hand2.charAt(0)).append(hand2.charAt(1)).toString();
    String h2c2 = new StringBuilder().append(hand2.charAt(2)).append(hand2.charAt(3)).toString();
    
    // Compare single cards by single cards
    if(h1c1.equals(h2c1)){
        overlap = 0;
    }
    else if(h1c1.equals(h2c2)){
        overlap = 0;
    }
    else if(h1c2.equals(h2c1)){
        overlap = 0;
    }
    else if(h1c2.equals(h2c2)){
        overlap = 0;
    }
    return overlap;
}


/**
 getEquity

 @param i hand1
 @param j hand2
 @return double returns the z(h1,h2) value
*/  

    public double getPayoff(int i,int j){
        return handPayoffs[i][j];
    }

    public static void main(String args[]) throws FileNotFoundException, IOException {
        Payoff payoff= new Payoff();
        FileWriter csvWriter =new FileWriter("payoff95.csv");
        for(int i=0;i<1326;i++){
            for(int j = 0; j<1326; j++){
                csvWriter.append(Double.toString(payoff.getPayoff(i,j)));
                csvWriter.append(",");
            }
            csvWriter.append("\n");
        }
        csvWriter.close();
    }
}
