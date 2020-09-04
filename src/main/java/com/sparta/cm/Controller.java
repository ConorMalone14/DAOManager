package com.sparta.cm;

import com.sparta.cm.dao.ConnectorManager;
import com.sparta.cm.dao.DAOManager;
import com.sparta.cm.dto.FileIOManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class Controller {
    private static final Logger logger = LogManager.getLogger();
    public static void run() {
        long start = System.nanoTime();
        try {
            ConnectorManager connectorManager = new ConnectorManager();
            DAOManager.createTable(connectorManager);
            //DAOManager.sendToDatabase(connectorManager, FileIOManager.readFromEmployeeFile());
            DAOManager.sendToDatabaseThreaded(connectorManager, FileIOManager.readFromEmployeeFile());
        } catch (IOException | SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.nanoTime();
        long total = end-start;
        double converted = (double)total;
        converted = converted/1000000;
        logger.info("Process completed in: " + converted + " milliseconds.");
    }
}
