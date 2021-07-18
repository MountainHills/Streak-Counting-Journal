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
import model.Streak;

@WebServlet(name = "StreakServlet", urlPatterns = {"/StreakServlet"})
public class StreakServlet extends HttpServlet {
    
    Connection con;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        con = DBConnector.getConnection();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        int userIndex = Integer.parseInt(session.getAttribute("userIndex").toString());
        
        System.out.println("The userIndex from the Streak Servlet is: " + userIndex);
        
        try 
        {
            // Checks if the records are empty
            PreparedStatement pstmtRecordCheck = con.prepareStatement("SELECT COUNT(*) FROM RECORDS WHERE USER_ID = " + userIndex);
            ResultSet rsRecordCheck = pstmtRecordCheck.executeQuery();
            Streak.isEmpty(rsRecordCheck);
            
            // If the record is empty then show default page.
            if (Streak.isRecordEmpty()) response.sendRedirect("index");
            
            // Setting the current Attempt
            PreparedStatement pstmtCurrentAttempt = con.prepareStatement("SELECT COUNT(*) FROM RECORDS WHERE USER_ID = " + userIndex);
            ResultSet rsCurrentAttempt = pstmtCurrentAttempt.executeQuery();
            Streak.setCurrentAttempt(rsCurrentAttempt);
            
            // Settting the start time of the current attempt
            int currentAttempt = Streak.getCurrentAttempt();
            System.out.println("The current attempt is: " + currentAttempt);
            PreparedStatement pstmtStreakStart = con.prepareStatement("SELECT START_STREAK FROM RECORDS WHERE USER_ID = " + userIndex
                                                                    + " AND ATTEMPT = " + currentAttempt);
            ResultSet rsStreakStart = pstmtStreakStart.executeQuery();
            Streak.setStartTimeStreak(rsStreakStart);
            
            // Setting the Current Streak
            Streak.setCurrentStreak();

            // Setting the best attempt from the records table.
            PreparedStatement pstmtBestAttempt = con.prepareStatement("SELECT * FROM RECORDS WHERE USER_ID = " + userIndex
                                    + "ORDER BY IS_HOURS ASC, DAYS DESC, SECONDS DESC");
            ResultSet rsBestAttempt = pstmtBestAttempt.executeQuery();
            Streak.setBestAttempt(rsBestAttempt);
               
            // Close result set and prepared statements.
            rsBestAttempt.close();
            rsStreakStart.close();
            rsCurrentAttempt.close();
            
            pstmtBestAttempt.close();
            pstmtStreakStart.close();
            pstmtCurrentAttempt.close();
        
            response.sendRedirect("index");
            
        } 
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
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
        processRequest(request, response);
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
