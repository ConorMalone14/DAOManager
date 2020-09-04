package com.sparta.cm.dao;


import com.sparta.cm.employees.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DAOManager {
    private static final Logger logger = LogManager.getLogger();
    public static void sendToDatabase(ConnectorManager connectorManager, ArrayList<Employee> employees) throws SQLException, IOException {
        connectorManager.truncate();
        long start = System.nanoTime();
        connectorManager.addEmployees(employees);
        long end = System.nanoTime();
        logger.info(connectorManager.countRows() + " Entries added to the database");
        long total = end-start;
        double converted = (double)total;
        converted = converted/1000000;
        logger.info("Data sent to database in: " + converted + " milliseconds.");
    }
    public static void sendToDatabaseThreaded(ConnectorManager connectorManager, ArrayList<Employee> employees) throws SQLException, IOException, InterruptedException {
        RunnableDAO runnableDAO = new RunnableDAO();
        runnableDAO.setEmployees(employees, connectorManager);
        connectorManager.truncate();
        long start = System.nanoTime();

        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<11;i++) {
            es.execute(runnableDAO);
        }
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);
        if(!es.isTerminated()){
            System.out.println("Timed out!");
        }

        long end = System.nanoTime();
        logger.info(connectorManager.countRows() + " Entries added to the database");
        long total = end-start;
        double converted = (double)total;
        converted = converted/1000000;
        logger.info("Data sent to database in: " + converted + " milliseconds.");
        System.out.println("Migration Complete");
    }
    public static void createTable(ConnectorManager connectorManager) throws SQLException {
        connectorManager.dropTable();
        connectorManager.createTable();

    }
}
