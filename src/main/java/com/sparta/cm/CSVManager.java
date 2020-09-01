package com.sparta.cm;

import com.sparta.cm.employees.Employee;
import com.sparta.cm.utility.FileIOManager;
import com.sparta.cm.utility.Printer;

import java.io.IOException;
import java.util.ArrayList;

public class CSVManager {
    public static double runCSV(boolean print){
        ArrayList<Employee> employees = null;
        long start=0;
        long end = 0;
        start = System.nanoTime();
        try{
            employees = FileIOManager.readFromEmployeeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        end = System.nanoTime();
        long total = end-start;
        double converted = (double)total;
        converted = converted/1000000;
        if(print == true){
            System.out.println("Completed in: " + converted + " milliseconds.");
            /*int count = 0;
            for(Employee e: employees){
            if (count <= 5) {
                Printer.printEmployee(e);
            }
            count++;
            }*/
        }
        return converted;
    }
}
