package com.sparta.cm.dao;

import com.sparta.cm.employees.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Properties;

public class ConnectorManager {
    private static final Logger logger = LogManager.getLogger();
    Connection connection;
    {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader("src/main/resources/login.properties"));
            String host = properties.getProperty("host");
            connection = DriverManager.getConnection("jdbc:mysql://" + host +"?serverTimezone=GMT", properties.getProperty("username"), properties.getProperty("password"));
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addEmployees(ArrayList<Employee> employees) throws IOException, SQLException {
        for(Employee e: employees){
            addEmployee(e);
        }
    }
    public void dropTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeLargeUpdate("DROP TABLE employees");
        statement.close();
    }
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeLargeUpdate("CREATE TABLE employees(\n" +
                "\tempID INT,\n" +
                "    prefix VARCHAR(5),\n" +
                "    firstName VARCHAR(20),\n" +
                "    middleInitial VARCHAR(1),\n" +
                "    lastName VARCHAR(20),\n" +
                "    gender VARCHAR(1),\n" +
                "    email VARCHAR(50),\n" +
                "    dateOfBirth DATE,\n" +
                "    dateOfJoining DATE,\n" +
                "    salary INT,\n" +
                "    PRIMARY KEY (empID)\n" +
                ")");
    }
    public Employee getEmployeeByIdFromDatabase(int ID) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT * FROM employees WHERE empID = " + ID);
        logger.debug(ID);
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        String empID = "" + resultSet.getInt(1);
        String namePrefix = resultSet.getString(2);
        String firstName = resultSet.getString(3);
        String middleInitial = resultSet.getString(4);
        String lastname = resultSet.getString(5);
        String gender = resultSet.getString(6);
        String email = resultSet.getString(7);
        LocalDate dob = convertToLocalDateViaMillisecond(resultSet.getDate(8));
        LocalDate doj = convertToLocalDateViaMillisecond(resultSet.getDate(9));
        int salary =resultSet.getInt(10);
        Employee e = new Employee(empID,namePrefix,firstName,middleInitial,lastname,gender,email,dob,doj,salary);
        statement.close();
        return e;
    }
    public LocalDate convertToLocalDateViaMillisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    private void addEmployee(Employee e) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO employees(empID, prefix, firstName, middleInitial, lastName, gender, email, dateOfBirth, dateOfJoining, salary)VALUES(" +
                "'"+ e.getEmpID()+"','"+ e.getNamePrefix()+"', '"+e.getFirstName()+"', '"+e.getMiddleInitial()+"', '"+e.getLastName()+"', '"+e.getGender()+"', '"+e.getEmail()+"', '"+e.getDob()+"', '"+e.getDateOfJoining()+"', "+e.getSalary()+")");
        statement.close();
    }
    public void truncate() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeLargeUpdate("TRUNCATE employees");
        statement.close();
    }

    public int countRows() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT COUNT(*) FROM employees");
        ResultSet resultSet = statement.getResultSet();
        resultSet.next();
        int count=Integer.parseInt(resultSet.getString(1));
        statement.close();
        return count;
    }
}
