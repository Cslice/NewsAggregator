/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SearchFilter;

import Database.DatabaseAPI;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cameronthomas
 */
public class EditWordsInDatabase {
    private String databaseId;
    private DatabaseAPI database;
    private String username;
    
    EditWordsInDatabase(String username)
    {    
        this.username = username;
        
        
        // Check if username and password are valid
        try
        {   
            // Get id of user in user table
            String selectQuery = "SELECT id from user WHERE username = '" + username + "'";
            database = new DatabaseAPI();
            ResultSet rs = database.readDatabase(selectQuery);
            rs.next();
            databaseId = rs.getString("id");
            rs.close();

//            while(rs.next())
//            {
//                if(rs.getString("username").equals(username))
//                {
//                   databaseId = rs.getString("id");
//                   break;
//                }
//            }
        }
        catch(SQLException se){
        //Handle errors for JDBC
        se.printStackTrace();
        }    
    }
    
    public boolean addWord(String word, String typeOfWord)
    {
        boolean wordExists = false;
        String selectQuery = "SELECT * from " + typeOfWord + " WHERE user_id = " + databaseId;
        ResultSet rs = database.readDatabase(selectQuery);
        
        // Check if username and password are valid
        try
        {       
            while(rs.next())
            {
                if(rs.getString("word").equals(word))
                {
                    wordExists = true;
                    break;
                }                
            }
            
            if(!wordExists)
            {
                String insertQuery = "INSERT INTO " + typeOfWord + " (word, user_id) "
                           + "VALUES (\"" + word + "\", " + databaseId + ")";
                database.updateDatabase(insertQuery);
                
//               rs.afterLast();
               
//                rs.updateObject("word", word);
//                rs.updateObject("user_id", databaseId);  
//                rs.updateRow();
                
               
            }
        }
        catch(SQLException se){
        //Handle errors for JDBC
        se.printStackTrace();
        } 
        finally
        {
            if(rs != null)
                try {
                    rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(EditWordsInDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            database.closeDatabase();
        }
           
        return wordExists;
    } 
    
    public void deleteWord(String word, String typeOfWord) 
    {
        String deleteQuery = "DELETE from " + typeOfWord + " where word='" + word
                             + "' and user_id=" + databaseId;
          
        database.updateDatabase(deleteQuery);    
    }
    
}
