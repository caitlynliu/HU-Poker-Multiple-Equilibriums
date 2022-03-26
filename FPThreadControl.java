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
        public static int computeIteration(FP fp, Strategies strategy1,Strategies strategy2, int player,int itr, int equilibrium, PrintWriter writer, double precision, double p1Param){
                // Computes the expected payoff for each of the 1326 field of strategy 1
                FPThread[] fpThreads = new FPThread[1326];
            for(int i = 0; i < 1326; i++){
                /** 
                if(i == 183){
                    System.out.println("test");
                }
                */
                    fpThreads[i] = new FPThread("Thread-"+i, fp, strategy1, strategy2, i, player, itr, writer, precision, p1Param);
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
        String[] p1strategies = {"p1strat.csv"};
        String[] p2strategies = {"p2strat.csv"};
        String strat1;
        String strat2;
        String[] stackSizes1 = {"20.0", "50.0", "100.0","200.0", "500.0", "1000.0"};
        double[] p1Params = {1.5,1.5,1.5,1.5,1.5,1.5};
        String[] stackSizes2 = {"20.0", "50.0", "100.0","200.0", "500.0", "1000.0"};
        String stackSize1;
        String stackSize2;
        double p1Param;
        double precision = 5.5;
        int TLno = 0;
        //writer.println();

        

        for(int i=0;i<p1strategies.length;i++){

            // compute for all strategies
            strat1= p1strategies[i];
            strat2= p2strategies[i];

            for(int m = 0; m<stackSizes1.length;m++){
                //for(int n = 0; n<stackSizes2.length;n++){
                    TLno++;
                    stackSize1 = stackSizes1[m]; 
                    p1Param = p1Params[m];
                    stackSize2 = stackSizes2[m]; 
                    System.out.println("P1 Stack size: "+stackSize1);
                    System.out.println("P1 param: "+p1Param);
                    System.out.println("P2 Stack size: "+stackSize2);
                    FP fp = new FP(strat1,strat2,stackSize1, stackSize2);
                    
            // Keep computing until equilibrium is reached
            while(equilibrium == 0){
                System.out.println("Itr "+itr);
                // writer.println("Itr "+i);
                 int equi1 = computeIteration(fp, fp.p1Strat, fp.p2Strat, 1,itr, equilibrium, writer, precision, p1Param);
                 int equi2 = computeIteration(fp, fp.p2Strat, fp.p1Strat, 2,itr, equilibrium, writer, precision, 0);
            if(equi1 == 1 && equi2 == 1){
                equilibrium = 1;
            }
            fp.setEq(1);
            itr++;
            } 
            
            // When equilibrium is reached, print strategies
            itr = 1;
            equilibrium =0;
            fp.printStrategies(startTime, precision,i,stackSize1);
            }
            //}
        }

    }

}
    

