package com.sparta.cm.dto;

import com.sparta.cm.employees.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FileIOManager {
    private static final Logger logger = LogManager.getLogger();
    public static ArrayList<Employee> readFromEmployeeFile() throws IOException {
        long start = Initialise();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/employees.csv"));
        bufferedReader.readLine();
        String line;
        ArrayList<Employee> array = new ArrayList<Employee>();
        Set<Integer> set = new HashSet<Integer>();
        final int[] unverified = {0};
        final int[] total = {0};

        while((line=bufferedReader.readLine())!=null ) {
            String finalLine = line;
            addEmployee(array, set, unverified, total, finalLine);

        }
        logInfo(start, unverified[0], total[0]);
        bufferedReader.close();
        return array;
    }

    private static void addEmployee(ArrayList<Employee> array, Set<Integer> set, int[] unverified, int[] total, String finalLine) {
        new Runnable() {
            @Override
            public void run() {
                total[0]++;
                String[] input = finalLine.split(",");
                try

                {
                    unverified[0] = verifyEmployee(unverified[0], finalLine, array, set, input);
                } catch(Exception e) {

                }

            }


        }.run();
    }

    private static long Initialise() throws IOException {
        long start = System.nanoTime();
        FileIOManager.writeToFile("Emp ID,Name Prefix,First Name,Middle Initial,Last Name,Gender,E Mail,Date of Birth,Date of Joining,Salary", false);
        return start;
    }

    private static int getUnverified(String line, int unverified) throws IOException {
        unverified++;
        writeToFile(line,true);
        return unverified;
    }

    private static void logInfo(long start, int unverified, int total) throws IOException {
        logger.info("Number of Unverified Employees: " + unverified);
        total = total - unverified;
        logger.info("Number of Verified Employees: " + total);
        long end = System.nanoTime();
        long totalTime = end- start;
        double converted = (double)totalTime;
        converted = converted/1000000;
        logger.info("CSV process Completed in: " + converted + " milliseconds.");
    }

    private static int verifyEmployee(int unverified, String line, ArrayList<Employee> array, Set<Integer> set, String[] input) throws IOException {
        LocalDate dob = inputValidator.convertToDate(input[7]);
        LocalDate doj = inputValidator.convertToDate(input[8]);
        int salary = Integer.parseInt(input[9]);
        int ID = Integer.parseInt(input[0]);
        if(inputValidator.isIDValid(input) && inputValidator.isEmailValid(input) && set.add(ID)) {
            Employee e = new Employee(input[0], input[1], input[2], input[3], input[4], input[5], input[6], dob, doj, salary);
            array.add(e);
        }else{
            //logger.trace("Unverified Employee");
            unverified = getUnverified(line, unverified);
        }
        return unverified;
    }

    public static void writeToFile(String line, boolean append) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/duplicates.txt", append));
        bufferedWriter.write(line +"\n");
        bufferedWriter.close();
    }
}
