/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SearchFilter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cameronthomas
 */
@WebServlet(name = "EditSearchParams", urlPatterns = {"/EditSearchParams"})
public class EditSearchParams extends HttpServlet {

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
            out.println("<title>Servlet EditSearchParams</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditSearchParams at " + request.getContextPath() + "</h1>");
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String word = request.getParameter("wordToAddOrDelete");
        String typeOfTransAction = request.getParameter("typeOfTransAction");
        String typeOfWord = "";
        String username = request.getSession(false).getAttribute("username").toString();
        EditWordsInDatabase editWord = new EditWordsInDatabase(username);
            
        switch (typeOfTransAction)
        {
            case "AddExcludeWord":
                typeOfWord = "words_to_exclude";
                editWord.addWord(word, typeOfWord);
                break;          
            case "DeleteExcludeWord":
                typeOfWord = "words_to_exclude";
                editWord.deleteWord(word, typeOfWord);
                break;      
            case "AddIncludeWord":
                typeOfWord = "words_to_include";
                editWord.addWord(word, typeOfWord);
                break;          
            case "DeleteIncludeWord":
                typeOfWord = "words_to_include";
                editWord.deleteWord(word, typeOfWord);
                break;       
        }


//        printStream2.println(word);
//        printStream2.println(username);
//        printStream2.println(typeOfWord);
//        printStream2.println(typeOfTransAction);


        
        //printStream2.println(insertQuery);
       
        new GenerateSearchParamsPage().doGet(request, response);
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

}
