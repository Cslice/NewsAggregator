/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author cameronthomas
 */
public class DatabaseAPI {
   String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   String databaseURL = "";
   String username;
   String password;
   Connection conn;
   Statement stmt;
   ResultSet rs;
   DatabaseInfo databaseInfo;
   
   public DatabaseAPI()
   {
        databaseInfo = new DatabaseInfo();
        databaseURL = databaseInfo.getDb_url();
        username = databaseInfo.getUser();
        password = databaseInfo.getPass();
        stmt = null;
        rs = null;
        
        startDatabaseConnection();  
   }
    
    /**
     *
     * 
     */
    private Connection startDatabaseConnection()
    {          
        try
        {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Open a connection
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
    public ResultSet readDatabase(String query)
    {  
        try
        {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query); 
            
      
        } 
        catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }
        
        return rs;
    }
    
    public void closeDatabase()
    {
        
        try 
        {
            if(rs != null)
            {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(stmt != null)
        {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(conn != null)
        {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }          
    }
    
    /**
     *
     * 
     */
    public void updateDatabase(String query)
    {
        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } 
        catch(SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        }  
        finally{
            //finally block used to close resources
            try{
               if(stmt!=null)
                  conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
            try{
               if(conn!=null)
                  conn.close();
            }catch(SQLException se){
               se.printStackTrace();
            }
        }//end finally try
    }
}
