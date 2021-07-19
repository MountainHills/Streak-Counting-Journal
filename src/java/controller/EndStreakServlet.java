package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Streak;

public class EndStreakServlet extends HttpServlet {
    Connection con;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        con = DBConnector.getConnection();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        
        HttpSession session = request.getSession();
        int userIndex = Integer.parseInt(session.getAttribute("userIndex").toString());
        
        try
        {
            // Gets the current Attempt
            int currentAttempt = Streak.getCurrentAttempt();
            
            // Gets the time when the button was clicked.
            long endTimeMillis = System.currentTimeMillis();
            Timestamp endTime = new Timestamp(endTimeMillis);
            
            String startTime = Streak.getStartTimeStreak();
            
            Timestamp startStreak = Timestamp.valueOf(startTime);
            long startTimeMillis = startStreak.getTime();

            long difference = Math.abs(endTimeMillis - startTimeMillis);

            // Checks whether time is days or Millis.
            long time = TimeUnit.MILLISECONDS.toDays(difference);
            boolean isHour = false;
            
            if (time == 0) {
                time = TimeUnit.MILLISECONDS.toHours(difference);
                isHour = true;
            }
            
            PreparedStatement pstmtEndStreak = con.prepareStatement("UPDATE RECORDS\n" +
                                        "SET END_STREAK = ?, DAYS = ?, IS_HOURS = ?, SECONDS = ?\n" +
                                        "WHERE USER_ID = " + userIndex + " AND ATTEMPT = " + currentAttempt);

            pstmtEndStreak.setTimestamp(1, endTime);
            pstmtEndStreak.setLong(2, time);
            pstmtEndStreak.setBoolean(3, isHour);
            pstmtEndStreak.setLong(4, difference);

            pstmtEndStreak.executeUpdate();
            
            // Create the next streak immediately after end.
            PreparedStatement pstmtNewStreak = con.prepareStatement("INSERT INTO RECORDS (USER_ID, ATTEMPT, START_STREAK)"
                                + "VALUES(?, ?, ?)");
            
            int nextAttempt = Streak.getCurrentAttempt() + 1;
            
            pstmtNewStreak.setInt(1, userIndex);
            pstmtNewStreak.setInt(2, nextAttempt);
            pstmtNewStreak.setTimestamp(3, endTime);

            pstmtNewStreak.executeUpdate();

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
