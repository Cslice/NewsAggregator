package Homepage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author cameronthomas
 */
public class GenerateHomepage {
    public void generatePage(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            //response.sendRedirect("homepage.jsp");
            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        } catch (IOException ex) {
            Logger.getLogger(GenerateHomepage.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ServletException ex)
        {
            ex.printStackTrace();
        }
            
    }
}
