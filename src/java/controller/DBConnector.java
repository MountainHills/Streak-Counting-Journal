package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    
    private static final String JDBC_USERNAME = "app";
    private static final String JDBC_PASSWORD = "app";
    private static final String JDBC_CLASS_NAME = "org.apache.derby.jdbc.ClientDriver";
    private static final String JDBC_DRIVER_URL = "jdbc:derby";
    private static final String JDBC_HOSTNAME = "localhost";
    private static final String JDBC_PORT = "1527";
    private static final String JDBC_DBNAME ="NoFapJournalDB";
        
    public static Connection getConnection()
    {
        Connection con = null;
        
        try 
        {
            // Accessing the database.
            Class.forName(JDBC_CLASS_NAME);
            
            String url = JDBC_DRIVER_URL + "://"
                    + JDBC_HOSTNAME + ":"
                    + JDBC_PORT + "/"
                    + JDBC_DBNAME;
            
            con = DriverManager.getConnection(url, JDBC_USERNAME, JDBC_PASSWORD);
            System.out.println("The connection has been made on: " + url);
        } 
        catch (ClassNotFoundException | SQLException e) {
            //  The database does not exist.
            e.printStackTrace();
        }
        
        return con;
    }
}
