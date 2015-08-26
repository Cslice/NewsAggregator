/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import java.sql.*;
/**
 *
 * @author cameronthomas
 */
public class DatabaseAPI {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static String db_url = "";

   //  DatabaseAPI credentials
   static String user = "";
   static String pass = "";
   
   static Connection conn = null;
   static Statement stmt = null;
    
    /**
     *
     * 
     */
    public Connection getDatabaseConnection(String databaseURL, String username, String password)
    {
        Connection conn = null;
        
        try
        {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(databaseURL,username,password);
        }
        catch(SQLException se){
           //Handle errors for JDBC
           se.printStackTrace();
        }
        catch(Exception e){
           //Handle errors for Class.forName
           e.printStackTrace();
        }
        
        return conn;
    }
    
    /**
     *
     * 
     */
    public ResultSet readDatabase(String databaseURL, String query,  String username, String password)
    {
        conn = getDatabaseConnection(databaseURL, username, password);
        ResultSet rs = null;
        
        try
        {
            stmt = conn.createStatement(); 
            stmt = conn.prepareStatement(username);
            rs = stmt.executeQuery(query);
        } 
        catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        
        return rs;
    }
    
    /**
     *
     * 
     */
    public void updateDatabase(String query)
    {
        
    }
    
}
