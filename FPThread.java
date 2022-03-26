import java.io.PrintWriter;
import java.lang.Math;

//
// FPThread.java
// Contain definitions and methods for the FPThread class needed to initialize the data fields
// This class implements the logic and actions to processed in parallel through threads
//
// Created by Caitlyn Liu on 2021-02-01
// Copyright ©2021 CaitlynLiu. All rights reserved.



public class FPThread extends Thread {
    private String threadName;
    private Strategies strat1;
    private Strategies strat2;
    private FP fp;
    private int player;
    private int h1;
    private int itr;
    private double p1Comp;
    private PrintWriter writer;
    private double precision; 

    FPThread(String name, FP fictitiousPlay, Strategies strategy1, Strategies strategy2, int hand, int playerNum, int iteration, PrintWriter printWriter, double equiPrecision, double p1Param){
        threadName = name;
        fp = fictitiousPlay;
        strat1 = strategy1;
        strat2= strategy2;
        h1 = hand;
        player = playerNum;
        itr = iteration;
        writer = printWriter;
        precision = equiPrecision;
        p1Comp = p1Param;
        //writer.println("Creating " + threadName);
       // System.out.println("Creating " + threadName);
        //writer.println("Creating " + threadName);
    }

    public void run(){
        //System.out.println("Running " + threadName);
        //writer.println("Running " + threadName);
        try{
            double a1Payoff = fp.expectedPayoff(strat1, strat2, h1, player);
            //double a2Payoff = expectedPayoff(strategy1, strategy2, 0, i);
            //System.out.println("h1:"+h1+" itr:"+itr+" player:"+player+" Bet Payoff:"+a1Payoff);
            //writer.println("h:"+h1+" itr:"+itr+" player:"+player+" Bet Payoff:"+a1Payoff);
            double newFq =0.0000;
            double newStrat;
            double oldFq= strat1.getFrequency(h1);
            double oldStrat= strat1.getStrategy(h1);
        
        // Compares expected payoff of action 1 (BET) and action 0 (FOLD)
        // betting results in higher payoff
        if(player == 1 && (a1Payoff+p1Comp) > 0 || player == 2 && (-a1Payoff) > 0 ){
                newFq = oldFq+1;
                newStrat = (newFq*1.0000)/(itr+1); // update iteration
                strat1.updateStrategy(h1, newStrat);
                strat1.updateFrequency(h1, newFq);
                //System.out.println("h1:"+h1+" itr:"+itr+" player:"+player+" Old Strat:"+ oldStrat+" "+"to "+newStrat+" OldFq:"+oldFq+" NewFq:"+newFq);
                //writer.println("h:"+h1+" itr:"+itr+" player:"+player+" Old Strat:"+ oldStrat+" "+"to "+newStrat+" OldFq:"+oldFq+" NewFq:"+newFq);
            }
            // folding results in higher payoff
            else{
                /** 
                if (h1 == 0){
                    System.out.println("AsKs test 2");
                }
                **/
                newStrat = (oldFq*1.0000)/(itr+1); // update iteration
                strat1.updateStrategy(h1, newStrat);
                //System.out.println("h1:"+h1+" itr:"+itr+" player:"+player+" Old Strat:"+ oldStrat+" "+"to "+newStrat+" No change in Fq:"+ oldFq);
                //writer.println("h:"+h1+" itr:"+itr+" player:"+player+" Old Strat:"+ oldStrat+" "+"to "+newStrat+" No change in Fq:"+ oldFq);
            }

            // Checks convergence criteria, if greater than 0.01 then criteria is not met
            if (Math.abs(newStrat - oldStrat) > Math.pow(0.1,precision)){
                //System.out.println("h1:"+h1+" itr:"+itr+" player:"+player+" Equilibrium not met");
               // writer.println("h:"+h1+" itr:"+itr+" player:"+player+" Equilibrium not met");
                fp.setEq(0);
            }

        }
        
        catch(Exception e){
            //System.out.println(threadName +" interrupted.");
           // writer.println(threadName +" interrupted.");
        }
       // System.out.println(threadName + " exiting");
       // writer.println(threadName + " exiting");
    }
    
}
