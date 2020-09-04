package com.sparta.cm.utility;

import com.sparta.cm.employees.Employee;

public class Printer {
    public static void printEmployee(Employee e){
        System.out.println(e.getEmpID());
        System.out.println(e.getNamePrefix());
        System.out.println(e.getFirstName());
        System.out.println(e.getMiddleInitial());
        System.out.println(e.getLastName());
        System.out.println(e.getGender());
        System.out.println(e.getEmail());
        System.out.println(e.getDob());
        System.out.println(e.getDateOfJoining());
        System.out.println(e.getSalary());
        System.out.println();

    }
}
