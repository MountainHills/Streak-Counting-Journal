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
        int userIndex = Integer.parseInt(session.getAttribute("userIndex").toString()) + 1;
        
        try 
        {
            // Setting the current Attempt
            PreparedStatement pstmtCurrentAttempt = con.prepareStatement("SELECT COUNT(*) FROM RECORDS WHERE USER_ID = " + userIndex);
            ResultSet rsCurrentAttempt = pstmtCurrentAttempt.executeQuery();
            Streak.setCurrentAttempt(rsCurrentAttempt);
            
            // Settting the start time of the current attempt
            int currentAttempt = Streak.getCurrentAttempt();
            PreparedStatement pstmtStreakStart = con.prepareStatement("SELECT START_STREAK FROM RECORDS WHERE USER_ID = " + userIndex
                                                                    + " AND ATTEMPT = " + currentAttempt);
            ResultSet rsStreakStart = pstmtStreakStart.executeQuery();
            Streak.setStartTimeStreak(rsStreakStart);
            
            // Setting the Current Streak
            Streak.setCurrentStreak();


            
            // Setting the best attempt from the records table.
            PreparedStatement pstmtDays = con.prepareStatement("SELECT DAYS FROM RECORDS WHERE USER_ID = " + userIndex);
            
            // Result of the Queries         
            ResultSet rsStreakEnd = pstmtStreakEnd.executeQuery();
            ResultSet rsDays = pstmtDays.executeQuery();
            
            //Resets the content of the model.
//            Records.resetData();
            
            // Places the queries to the model.
            
            
            Records.setStreakEnd(rsStreakEnd);
            Records.setDays(rsDays);
            
            // Close result set and prepared statements.
            rsDays.close();
            rsStreakEnd.close();
            rsStreakStart.close();
            rsAttempts.close();
            
            pstmtDays.close();
            pstmtStreakEnd.close();
            pstmtStreakStart.close();
            pstmtAttempts.close();
        
            session.setAttribute("records", "true");
        
            response.sendRedirect("records");
            
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
