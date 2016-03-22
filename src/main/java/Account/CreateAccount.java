/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Account;

import Database.DatabaseAPI;
import Database.DatabaseInfo;
import Homepage.GenerateHomepage;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@WebServlet(name = "CreateAccount", urlPatterns = {"/CreateAccount"})
public class CreateAccount extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateAccount</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateAccount at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if(validateNewUsername(username))
        {
            insertNewUser(username, password);
            HttpSession session = request.getSession(true);
            session.setAttribute("username", username);
            session.setAttribute("password", password);
            
            new GenerateHomepage().generatePage(request, response); 
        }
        else
            response.sendRedirect("invalidNewAccount.html"); 
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    private boolean validateNewUsername(String username)
    {
        boolean validUsername = true;
        String query = "Select username FROM user";
        DatabaseAPI database = new DatabaseAPI();
        ResultSet rs = database.readDatabase(query);
        
        // Check if username and password are valid
        try
        {       
            while(rs.next())
            {
                if(rs.getString("username").equals(username))
                {
                    validUsername = false;  
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
        return validUsername;
    }
    
    private void insertNewUser(String username, String password)
    {
        String query = "INSERT INTO user(username,password) "
                + "values(\"" + username + "\", \"" + password + "\");";
        new DatabaseAPI().updateDatabase(query);  
    }

}
