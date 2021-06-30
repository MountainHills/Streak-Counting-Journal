package controller;

import java.sql.Connection;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RecordServlet", urlPatterns = {"/RecordServlet"})
public class RecordServlet extends HttpServlet {
    
    Connection con;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        con = DBConnector.getConnection();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
       
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
