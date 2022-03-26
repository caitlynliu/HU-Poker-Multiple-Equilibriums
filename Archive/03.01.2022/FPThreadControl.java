//
// FPThreadControl.java
// Contain definitions and methods for the FPThreadControl class needed to initialize the data fields
// This class contains the the main function that initializes and runs the 1326 threads
//
// Created by Caitlyn Liu on 2021-02-01
// Copyright ©2021 CaitlynLiu. All rights reserved.

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class FPThreadControl {
        /**
         computeIteration

        Takes startegies at its current state and calculates the payoff of action (1 BET) and action (0 FOLD)
        Compares which action to take and activates the strategy frequency as such
        @param strategy1 Strategies matrix for first strategy
        @param strategy2 Strategies matrix for second strategy
        @param itr integer indicating the iteration 
        @return int - 1 has not reached an equilibrium, 0 has reach FileNotFoundException or IOException if the data provided is invalid
        */  
        public static int computeIteration(FP fp, Strategies strategy1,Strategies strategy2, int player,int itr, int equilibrium, PrintWriter writer, double precision){
                // Computes the expected payoff for each of the 1326 field of strategy 1
                FPThread[] fpThreads = new FPThread[1326];
            for(int i = 0; i < 1326; i++){
                /** 
                if(i == 183){
                    System.out.println("test");
                }
                */
                    fpThreads[i] = new FPThread("Thread-"+i, fp, strategy1, strategy2, i, player, itr, writer, precision);
                    fpThreads[i].start();
            }
            for(FPThread thread : fpThreads){
                try{
                    thread.join();
           
                } catch(InterruptedException e){
                    System.out.println(thread +" interrupted.");
                }
                  
            }

           // System.out.println("itr:"+itr+" player:"+player+" Threads completed!");
            //writer.println("itr:"+itr+" player:"+player+" Threads completed!");
            return fp.getEq();
        }

    public static void main(String args[]) throws FileNotFoundException, IOException{
        final long startTime = System.nanoTime();
        int equilibrium = 0;
        int itr = 1;
        PrintWriter writer = new PrintWriter("debugLog.txt", "UTF-8");
        String[] p1strategies = {"p1strat.csv, p1strat1s.csv"};
        String[] p2strategies = {"p2strat.csv, p2strat1s.csv"};
        String strat1;
        String strat2;
        String[] stackSizes = {"10"};
        String stackSize;
        double precision = 5;
        //writer.println();

        

        for(int i=0;i<p1strategies.length;i++){

            // compute for all strategies
            strat1= p1strategies[i];
            strat2= p2strategies[i];

            for(int j = 0; j<1;j++){
                stackSize = stackSizes[j]; // compute for each stack size
                System.out.println("Stack size: "+stackSize);
                FP fp = new FP(strat1,strat2,stackSize);
                    
            // Keep computing until equilibrium is reached
            while(equilibrium == 0){
                System.out.println("Itr "+itr);
                // writer.println("Itr "+i);
                 int equi1 = computeIteration(fp, fp.p1Strat, fp.p2Strat, 1,itr, equilibrium, writer, precision);
                 int equi2 = computeIteration(fp, fp.p2Strat, fp.p1Strat, 2,itr, equilibrium, writer, precision);
            if(equi1 == 1 && equi2 == 1){
                equilibrium = 1;
            }
            fp.setEq(1);
            itr++;
            } 
            
            // When equilibrium is reached, print strategies
            itr = 1;
            equilibrium =0;
            fp.printStrategies(startTime, precision,i,stackSize);
            }


        }

    }

}
    

