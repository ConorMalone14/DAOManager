package com.sparta.cm;

import com.sparta.cm.employees.Employee;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class ConnectorManager {
    Connection connection;
    {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/login.properties"));
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/csvproject?serverTimezone=GMT", properties.getProperty("username"), properties.getProperty("password"));
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addEmployees(ArrayList<Employee> employees) throws IOException, SQLException {
        int count = 0;
        for(Employee e: employees){
            addEmployee(e);
        }
    }
    private void addEmployee(Employee e) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO employees(empID, prefix, firstName, middleInitial, lastName, gender, email, dateOfBirth, dateOfJoining, salary)VALUES(" +
                "'"+ e.getEmpID()+"','"+ e.getNamePrefix()+"', '"+e.getFirstName()+"', '"+e.getMiddleInitial()+"', '"+e.getLastName()+"', '"+e.getGender()+"', '"+e.getEmail()+"', '"+e.getDob()+"', '"+e.getDateOfJoining()+"', "+e.getSalary()+")");
    }
    public void truncate() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeLargeUpdate("TRUNCATE employees");
    }

    public void countRows() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT COUNT(*) FROM employees");
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        System.out.println(resultSet.getString(1));
    }
}
