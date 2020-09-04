package com.sparta.cm.dao;

import com.sparta.cm.employees.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RunnableDAO implements Runnable{
    ArrayList<Employee> employees;
    static int index = 0;
    private static final Logger logger = LogManager.getLogger();

    public void setEmployees(ArrayList<Employee> employees, ConnectorManager cm){
        this.employees = employees;
    }

    @Override
    public void run() {
        ArrayList<Employee> toAdd = new ArrayList<>();
        synchronized (this){
            for(int i=0;i< employees.size()/10; i++){
                if (index< employees.size()){
                    toAdd.add(employees.get(index));
                    //System.out.println(Thread.currentThread().getName() + " Index:" + index);
                    index++;
                }
            }
        }
        try {
            logger.trace("starting to add");
            ConnectorManager connectorManager = new ConnectorManager();
            connectorManager.addEmployees(toAdd);
            logger.trace("Successfully added: " + toAdd.size());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
