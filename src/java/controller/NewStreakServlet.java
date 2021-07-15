/*
    This servlet will be only used once when there are no records
    from the user. It will begin at Attempt # 1 with the specified
    time when the button was clicked and user index.
*/

package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NewStreakServlet extends HttpServlet {
    Connection con;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        con = DBConnector.getConnection();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
                        // Enters the parameter to the database.     
        
        HttpSession session = request.getSession();
        int userIndex = Integer.parseInt(session.getAttribute("userIndex").toString());
                                
        try
        {
            // Gets the time when the button was clicked.
            long startTimeMillis = System.currentTimeMillis();
            Timestamp startTime = new Timestamp(startTimeMillis);
            
            PreparedStatement pstmtStartStreak = con.prepareStatement("INSERT INTO RECORDS (USER_ID, ATTEMPT, START_STREAK)"
                                + "VALUES(?, ?, ?)");

            pstmtStartStreak.setInt(1, userIndex);
            pstmtStartStreak.setInt(2, 1);
            pstmtStartStreak.setTimestamp(3, startTime);

            pstmtStartStreak.executeUpdate();

            response.sendRedirect("StreakServlet");
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
