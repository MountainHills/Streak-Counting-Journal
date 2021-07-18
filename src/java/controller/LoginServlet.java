package controller;

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

public class LoginServlet extends HttpServlet {
    
    Connection con;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        
        con = DBConnector.getConnection();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Gets the username and password from login.jsp
        String username = request.getParameter("usernameLogin");
        String password = request.getParameter("passwordLogin");
        
        // Created ArrayLists to store data from USER table.
        ArrayList<String> usernameList = new ArrayList<>();
        ArrayList<String> passwordList = new ArrayList<>();
        
        try 
        {
            // Gets the USERNAME and PASSWORD columns in the USER table
            PreparedStatement pstmtUsers = con.prepareStatement("SELECT USERNAME, PASSWORD FROM USERS");
            ResultSet userRecords = pstmtUsers.executeQuery();
            
            // Place data in ArrayLists for easier checking.
            while(userRecords.next())
            {
                usernameList.add(userRecords.getString("USERNAME"));
                passwordList.add(userRecords.getString("PASSWORD"));
            }
            
            userRecords.close();
            pstmtUsers.close();
            
        } 
        catch (SQLException sqle) 
        {
            sqle.printStackTrace();
        } 
        
        // TODO: Error Handling: If username and passwords are empty from login.jsp
        // TODO: Check whether the connection is null. If the connection is null don't proceed below.
        
        // Encrypted password from login.jsp to compare to the database.
        password = Security.encrypt(password);
        
        // Checks whether the username exists in the database
        if (usernameList.contains(username)) 
        {
            // Gets the index of the password based on the position of the username.
            int passwordIndex = usernameList.indexOf(username);
            System.out.println("The index of the password for " + username + " is " + passwordIndex);
            
            // Checks if the passwords from the database and login.jsp match.
            if (password.equals(passwordList.get(passwordIndex)))
            {
                //When the password is valid, it creates a session and proceed to index.jsp.
                HttpSession session = request.getSession();
                
                session.setAttribute("user", username);
                session.setAttribute("userIndex", passwordIndex + 1);
                
                response.sendRedirect("StreakServlet");
            }
            else
            {
                response.sendRedirect("login");
                // TODO: The password is incorrect error handling.
            }
        }
        else 
        {
            response.sendRedirect("login");
            // TODO: The username doesn't exist error handling.
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
