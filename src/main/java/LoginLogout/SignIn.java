package LoginLogout;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.DatabaseAPI;
import Database.DatabaseInfo;
import Homepage.GenerateHomepage;
import TestAndDebug.WriteFile;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author cameronthomas
 */
@WebServlet(urlPatterns = {"/SignIn"})
public class SignIn extends HttpServlet {
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username= "";
        String password = "";
        
        // Get the session
        HttpSession session = request.getSession(true);
        
        // New session was created
        if(session.getAttribute("username") == null &&
           session.getAttribute("password") == null)
        {
            // Get username and password user entered
            username = request.getParameter("username");
            password = request.getParameter("password");   
            
            // Set username and password in seesion
            session.setAttribute("username", username);
            session.setAttribute("password", password);   
        }

        // Session already created
        else
        {  
            // Get username and password from request
           username = session.getAttribute("username").toString();
           password = session.getAttribute("password").toString();        
        }
            
        // If username and password are valid go to homepage
        if(validateLogin(username, password))
            new GenerateHomepage().generatePage(request, response);  
        
        // If username and password are invalid go to error page
        else
        {
            session.invalidate();
            response.sendRedirect("invalidLogin.html"); 
        }
    }
    
    /**
     *
     * 
     */
    private boolean validateLogin(String username, String password)
    {
        boolean validLogin = false;
        int databaseId = 0;
        String query = "Select * FROM user";
        DatabaseAPI database = new DatabaseAPI();
        ResultSet rs = database.readDatabase(query);
        
        // Check if username and password are valid
        try
        {       
            while(rs.next())
            {
                if(rs.getString("username").equals(username) &&
                   rs.getString("password").equals(password))
                {
                    validLogin = true;
                    databaseId = rs.getInt("id");
                    break;
                }
            }
        }
        catch(SQLException se){
        //Handle errors for JDBC
        se.printStackTrace();
        } 
        finally
        {
            database.closeDatabase();
        }
        return validLogin;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
}
