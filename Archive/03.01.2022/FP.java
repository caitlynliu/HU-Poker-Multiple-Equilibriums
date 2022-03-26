//
// FP.java
// Contain definitions and methods for the FP class needed to initialize, access, and set Country data fields
//
// Created by Caitlyn Liu on 2021-01-26
// Copyright ©2021 CaitlynLiu. All rights reserved.

import java.io.*;
import java.lang.StringBuffer;

/**
 FP


 The constructor for the fictitious play class. Initializes the data fields and structure of FP class.

 @throws an FileNotFoundException or IOException if the data provided is invalid
*/

public class FP {
    Strategies p1Strat, p2Strat;
    HandPayoff payoffMatrix;
    Converter converter;
    int equilibrium;
    public FP(String strat1, String strat2, String stackSize) throws FileNotFoundException, IOException{

        // Sotre candidate strategies
        p1Strat = new Strategies(strat1);
        p2Strat = new Strategies(strat2);
        
        // Store showdown win percentages in a matrix (1326x1326 = 1,758,276 fields to process)
        String payoffFile = "payoff"+stackSize+".csv";
        payoffMatrix = new HandPayoff(payoffFile);
        
        // Store index values for hole cards
        converter = new Converter("converter.txt");

        // Set initial equilibrium
        equilibrium = 1;
    }
    

/**
 expectedPayoff

 @param strategy1 Strategies matrix for first strategy
 @param strategy2 Strategies matrix for second strategy
 @param h1 int indicates hand1
 @return double the payoff calculated from the strategies at its current state, respective action, and hand1
*/  
    public double expectedPayoff(Strategies strategy1, Strategies strategy2, int h1, int player){
        double payoff = 0;
        double newPayoff = 0;
        // Iterates through hand2
        for(int i = 0; i<1326; i++){
            // If there is an overlap then the probability of 0 is assigned to hand probability
            if(player==1){
                newPayoff = payoffMatrix.getPayoff(h1,i);
            }
            else{
                newPayoff = payoffMatrix.getPayoff(i,h1); 
            }
            /** 
            if(checkOverlap(h1,i) == 0) {
                newPayoff = 0;
            }
            **/
            // Total payoff is calculated after iterating through all of hand2
            payoff = (payoff + (newPayoff*strategy2.getStrategy(i)));
        }
        return payoff;
    }


    /**
        printStrategies
        Print both strategy matrices at equilibrium
    */

    public void printStrategies(long time, double precision, int strategy, String stacksize) throws IOException{
        System.out.print("Hand Strategies \n");
        StringBuilder fileName = new StringBuilder();
        fileName.append("strategies");
        fileName.append(Integer.toString((int)precision));
        fileName.append(Integer.toString(strategy));
        fileName.append(stacksize);
        fileName.append(".csv");
        FileWriter stratWriter =new FileWriter(fileName.toString());
        final long endTime = System.nanoTime();
        final double duration = (endTime - time)/60_000_000_000.0;
        stratWriter.append("Program Duration");
        stratWriter.append(",");
        stratWriter.append(Double.toString(duration));
        stratWriter.append(",");
        stratWriter.append("mins");
        stratWriter.append("\n");
        stratWriter.append("\n");
        stratWriter.append("Hand");
        stratWriter.append(",");
        stratWriter.append("P1 - F");
        stratWriter.append(",");
        stratWriter.append("P2 - F");
        stratWriter.append(",");
        stratWriter.append("P1 - S");
        stratWriter.append(",");
        stratWriter.append("P2 - S");
        stratWriter.append("\n");
        for(int i = 0; i<1326; i++){
            stratWriter.append(converter.getHand(i));
            stratWriter.append(",");
            stratWriter.append(Double.toString(p1Strat.getFrequency(i)));
            stratWriter.append(",");
            stratWriter.append(Double.toString(p2Strat.getFrequency(i)));
            stratWriter.append(",");
            stratWriter.append(Double.toString(p1Strat.getStrategy(i)));
            stratWriter.append(",");
            stratWriter.append(Double.toString(p2Strat.getStrategy(i)));
            stratWriter.append("\n");
            
        }
        stratWriter.close();
    }

    /**
     * Set equilibrium
     * 
     */

     public void setEq(int state){
         equilibrium = state;
     }

    /**
     * Get equilibrium
     * 
     */
    public int getEq(){
        return equilibrium;
    }
}


