package controller;

import exception.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nl.captcha.Captcha;

public class RegisterServlet extends HttpServlet {
    
    Connection con;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        con = DBConnector.getConnection();
    }
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the parameters from register.jsp
        String username = request.getParameter("usernameRegister").trim();
        String password = request.getParameter("passwordRegister");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Check if password and confirmPassword matches (CREATE ERROR PAGE)
        if (!password.equals(confirmPassword)) throw new IncorrectPasswordsException();
        
        // Declare variables to check the record size.
        int recordSize = 1;
        
        try
        { 
            PreparedStatement pstmtUsernames = con.prepareStatement("SELECT USERNAME FROM USERS");
            PreparedStatement pstmtUserRecords = con.prepareStatement("SELECT COUNT(*) FROM USERS");
            
            ResultSet recordsUsernames = pstmtUsernames.executeQuery();
            ResultSet recordsAllUsers = pstmtUserRecords.executeQuery();
            
            // Gets the number of records in the USERS table.
            while (recordsAllUsers.next())
            {
                recordSize = recordsAllUsers.getInt(1);
            }
            
            ArrayList<String> usernameList = new ArrayList<>();
            while (recordsUsernames.next())
            {
                usernameList.add(recordsUsernames.getString("USERNAME"));
            }
            
            // Checks the username from register.jsp if it is unique.
            if (usernameList.contains(username)) throw new NotUniqueUsernameException();
            
            pstmtUsernames.close();
            pstmtUserRecords.close();
            pstmtUsernames.close();
            pstmtUserRecords.close();
        }
        catch (SQLException sqle) 
        {
            sqle.printStackTrace();
        }
        
        // TODO: Set up the CAPTCHA and get the Parameter of the Captcha.
        HttpSession session = request.getSession(false);
        Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);  
        request.setCharacterEncoding("UTF-8");  
        String captchaAnswer = request.getParameter("captcha");
              
        if (captcha.isCorrect(captchaAnswer))
        {
            try 
            {
                // Enters the parameter to the database.
                PreparedStatement pstmtRegister = con.prepareStatement("INSERT INTO USERS (USER_ID, USERNAME, PASSWORD)"
                                                + "VALUES(?, ?, ?)");
                
                pstmtRegister.setInt(1, recordSize + 1);
                pstmtRegister.setString(2, username);
                pstmtRegister.setString(3, Security.encrypt(password));
                
                pstmtRegister.executeUpdate();
                
                // Removes the Captcha from the Session, it won't be used anymore.
                session.removeAttribute(Captcha.NAME);
                session.invalidate();
                
                response.sendRedirect("login");
      
            }
            catch (SQLException sqle)
            {
                sqle.printStackTrace();
            }
        }
        else {
            throw new IncorrectCaptchaException();
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
