package com.sparta.cm.utility;

import com.sparta.cm.employees.Employee;
import com.sparta.cm.employees.inputValidator;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileIOManager {
    public static ArrayList<Employee> readFromEmployeeFile() throws IOException {
        FileIOManager.writeToFile("Emp ID,Name Prefix,First Name,Middle Initial,Last Name,Gender,E Mail,Date of Birth,Date of Joining,Salary", false);
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/employees.csv"));
        bufferedReader.readLine();
        String line;
        int count=0;
        ArrayList<Employee> array = new ArrayList<Employee>();
        Set<Integer> set = new HashSet<Integer>();

        while((line=bufferedReader.readLine())!=null ){
            String[] input = line.split(",");

            try {
                LocalDate dob = inputValidator.convertToDate(input[7]);
                LocalDate doj = inputValidator.convertToDate(input[8]);
                int salary = Integer.parseInt(input[9]);
                int ID = Integer.parseInt(input[0]);
                if(inputValidator.isIDValid(input) && inputValidator.isEmailValid(input) && set.add(ID)) {
                    Employee e = new Employee(input[0], input[1], input[2], input[3], input[4], input[5], input[6], dob, doj, salary);
                    array.add(e);
                    count++;
                }else{
                    System.out.println("Invalid Line");
                    writeToFile(line, true);
                }
            } catch (Exception e) {
                //Do nothing
                System.out.println("Invalid Line");
                e.printStackTrace();
                writeToFile(line,true);

            }
        }
        bufferedReader.close();
        return array;
    }
    public static void writeToFile(String line, boolean append) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/duplicates.txt", append));
        bufferedWriter.write(line +"\n");
        bufferedWriter.close();
    }
}
