/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SearchFilter;

import Database.DatabaseAPI;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cameronthomas
 */
@WebServlet(name = "GenerateSearchParamsPage", urlPatterns = {"/GenerateSearchParamsPage"})
public class GenerateSearchParamsPage extends HttpServlet {
    
    private List<String> excludeWordList;
    private List<String> includeWordList;
    private DatabaseAPI database;
    private ResultSet rs;
    
    public GenerateSearchParamsPage() {
        
        excludeWordList = new ArrayList();
        includeWordList = new ArrayList();
        database = new DatabaseAPI();
        
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
        String username = request.getSession(false).getAttribute("username").toString();

        
        setupWordLists(username);
        
        request.setAttribute("excludeWordList", excludeWordList);
        request.setAttribute("includeWordList", includeWordList);
        request.getRequestDispatcher("editSearchParams.jsp").forward(request, response);   
        
        //response.sendRedirect("editSearchParams.jsp");
        
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
             
    }
    
    private void setupWordLists(String username) throws IOException
    {
//         final OutputStream os2 = new FileOutputStream("/Users/cameronthomas/Desktop/word.txt");
//            final PrintStream p = new PrintStream(os2);
        
        
            //p.println(username);
        String query = "SELECT id from user WHERE username = '" + username + "'";
         //                   p.println(query);

        rs = database.readDatabase(query);
        
       // p.println("After d");

        

        try {
            rs.next();
            String databaseId = rs.getString("id");
            rs.close();
            
            query = "select * from words_to_exclude WHERE user_id = " + databaseId;
            
            rs = database.readDatabase(query);
                        
          //  p.println("asdfdsf" +databaseId);

            
            excludeWordList.clear();
            
            while(rs.next())
                excludeWordList.add(rs.getString("word")); 
           
         //   p.println("2");
            
            query = "select * from words_to_include WHERE user_id = " + databaseId;
            
            rs = database.readDatabase(query);
            
            includeWordList.clear();
                        
             while(rs.next())
                includeWordList.add(rs.getString("word"));
             
             
          //  p.println("3");

            //     p.println(excludeWordList.toString());
           // p.println(includeWordList.toString());
        } catch (SQLException ex) {
            Logger.getLogger(GenerateSearchParamsPage.class.getName()).log(Level.SEVERE, null, ex);
        } 
    } 
}
