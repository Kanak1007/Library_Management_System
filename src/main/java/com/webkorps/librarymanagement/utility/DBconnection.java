package com.webkorps.librarymanagement.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
 private static final String URL = "jdbc:mysql://localhost:3306/librarymanagementsystem"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "root";  
    
    public static Connection getConnection() {
    Connection con = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure the driver is loaded
        con = DriverManager.getConnection(URL, USER, PASSWORD);
        
        // Debugging Statement
        System.out.println("Database connection established: " + (con != null));
        
    }
    catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();  // Log the error
    }
    return con;
}

    
}
