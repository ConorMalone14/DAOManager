package com.sparta.cm;

import com.sparta.cm.employees.Employee;
import com.sparta.cm.utility.FileIOManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBOManager {
    public static void runQueries(){
        ConnectorManager connectorManager = new ConnectorManager();
        try {
            ArrayList<Employee> employees= FileIOManager.readFromEmployeeFile();
            connectorManager.truncate();
            long start = System.nanoTime();
            connectorManager.addEmployees(employees);
            long end = System.nanoTime();
            connectorManager.countRows();
            long total = end-start;
            double converted = (double)total;
            converted = converted/1000000;
            System.out.println("Completed in: " + converted + " milliseconds.");

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

    }
}
