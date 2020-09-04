package com.sparta.cm;

import com.sparta.cm.dao.ConnectorManager;
import com.sparta.cm.dto.FileIOManager;
import com.sparta.cm.employees.Employee;
import com.sparta.cm.utility.Printer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOManagerTester {
    @Test
    public void getCompareValues() {
        try {
            ConnectorManager connectorManager = new ConnectorManager();
            ArrayList<Employee> employees= FileIOManager.readFromEmployeeFile();
            int count = 0;
            for(Employee employee: employees){
                if (count<5) {
                    Employee test = connectorManager.getEmployeeByIdFromDatabase(Integer.parseInt(employee.getEmpID()));
                    System.out.println("From array");
                    Printer.printEmployee(employee);
                    System.out.println("From database");
                    Printer.printEmployee(test);
                }
                count++;
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }
}
