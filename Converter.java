// Converter.java
// Contain definitions and methods for the Converter class needed to initialize the data fields
//
// Created by Caitlyn Liu on 2021-01-26
// Copyright ©2021 CaitlynLiu. All rights reserved.


import java.io.*;
import java.util.StringTokenizer;


/**
 Converter

 The constructor for the Converter class. Initializes the data fields Converter class.
 @throws an FileNotFoundException or IOException if the data provided is invalid
*/

public class Converter{
    String[] index;
    public Converter(String conversion)throws FileNotFoundException, IOException{
        index = new String[1326];
        BufferedReader br = new BufferedReader(new FileReader(conversion));
        String line = null;
        int i = 0;
        while((line = br.readLine())!= null){
            StringTokenizer st = new StringTokenizer(line, " ");
            st.nextToken();
            index[i] = st.nextToken(); 
            i++;
            }
        br.close();
    }
/**
 getEquity

 @param hand int index representation of hand
 @returns String returns string representation of hand
*/  
    public String getHand(int hand){
        return index[hand];
    }

/**
 printConverter

Prints conversion

*/  
    public void printConverter(){
        for(int i = 0; i < 1326; i++){
            System.out.print(index[i]);
            System.out.print("\n");
        }
    }
}
