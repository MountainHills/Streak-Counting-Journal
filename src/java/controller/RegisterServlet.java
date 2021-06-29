package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try 
        {
            // Accessing the database.
            Class.forName(config.getInitParameter("jdbcClassName"));
            String username = config.getInitParameter("dbUserName");
            String password = config.getInitParameter("dbPassword");
            StringBuffer url = new StringBuffer(config.getInitParameter("jdbcDriverURL")).append("://")
                .append(config.getInitParameter("dbHostName"))
                .append(":")
                .append(config.getInitParameter("dbPort"))
                .append("/")
                .append(config.getInitParameter("databaseName"));
            con = DriverManager.getConnection(url.toString(),username,password);         
        } 
        catch (Exception e) {
            //  The database does not exist.
            e.printStackTrace();
        }
    }
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO: Get the parameters from register.jsp
        String username = request.getParameter("usernameRegister").trim();
        String password = request.getParameter("passwordRegister");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // TODO: Check whether the parameters are all filled.
        if (username.equals("") || password.equals("") || confirmPassword.equals(""))
        {
            // TODO: Create an error handling page for empty forms.
            System.out.println("Enter complete parameters.");
        }
        
        // Declare variables to check the record size.
        int recordSize = 1;
        
        try
        { 
            PreparedStatement pstmtRecord = con.prepareStatement("SELECT USERNAME FROM USERS");
            ResultSet rs = pstmtRecord.executeQuery();
            
            while (rs.next())
            {
                // Checks whether the username from register.jsp is unique.
                if (username.equals(rs.getString("USERNAME")))
                {
                    // TODO: Throw if the username is not unique!
                }
                // Sets the record size.
                if (rs.isLast())
                {
                    recordSize = rs.getRow();
                }
            }
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
