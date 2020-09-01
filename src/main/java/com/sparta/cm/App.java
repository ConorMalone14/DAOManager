package com.sparta.cm;

import com.sparta.cm.employees.Employee;
import com.sparta.cm.utility.FileIOManager;
import com.sparta.cm.utility.Printer;

import java.io.IOException;
import java.util.ArrayList;

public class App
{
    public static void main( String[] args )
    {
        //CSVManager.runCSV(true);
        DBOManager.runQueries();
    }
}
