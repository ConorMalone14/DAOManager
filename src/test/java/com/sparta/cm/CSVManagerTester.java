package com.sparta.cm;

import org.junit.jupiter.api.Test;

public class CSVManagerTester {
    @Test
    public void testAvg(){
        double total=0;
        for(int i=0;i<1000;i++){
             total =total+CSVManager.runCSV(false);
        }
        total = total/1000;
        System.out.println("Completed on average: " + total + " milliseconds.");
    }
}
