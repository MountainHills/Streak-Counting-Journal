package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Records;

@WebServlet(name = "RecordServlet", urlPatterns = {"/RecordServlet"})
public class RecordServlet extends HttpServlet {
    
    Connection con;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        con = DBConnector.getConnection();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        int userIndex = Integer.parseInt(session.getAttribute("userIndex").toString());
        
        System.out.println();
        
        try (PreparedStatement pstmtRecords = con.prepareStatement("SELECT ATTEMPT, START_STREAK, END_STREAK, DAYS, IS_HOURS\n" +
                                                                    "FROM RECORDS\n" +
                                                                    "WHERE USER_ID = " + userIndex + "\n" +
                                                                    "ORDER BY ATTEMPT DESC"))
        {   
            // Result of the Queries
            ResultSet rsRecords = pstmtRecords.executeQuery();
            
            //Resets the content of the model.
            Records.resetData();
            System.out.println("The data from records has been deleted.");
            
            // Places the queries to the model.
            Records.setRecords(rsRecords);
            
            rsRecords.close();
            
            session.setAttribute("records", "true");
            response.sendRedirect("records");
            
        } 
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
