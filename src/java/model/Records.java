package model;

import controller.DBConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Records {
    // TODO: Get information from the database and place them in Lists to be accessed by records.jsp
    
    private static Connection con;
    private static int userIndex;
    private static ArrayList<Integer> attemptsList = new ArrayList<>();
    private static ArrayList<String> streakStartList = new ArrayList<>();
    private static ArrayList<String> streakEndList = new ArrayList<>();
    private static ArrayList<Integer> daysList = new ArrayList<>();
    
    // Constructor
    public Records(int userIndex) 
    {
        // Establish  connection to the database
        con = DBConnector.getConnection();
        this.userIndex = userIndex + 1;
    }
    
    // Getters
    public static ArrayList<Integer> getAttempts() 
    {
        if (con != null) 
        {
            try 
            {
                PreparedStatement query = con.prepareStatement("SELECT ATTEMPT FROM RECORDS WHERE USER_ID = " + userIndex);
                ResultSet rs = query.executeQuery();

                while(rs.next())
                {
                    attemptsList.add(rs.getInt("ATTEMPT"));
                }

                rs.close();
                query.close();
                System.out.println();
                return attemptsList;
            } 
            catch (SQLException sqle) 
            {
                sqle.printStackTrace();
            } 
        }
        return null;
    }
    
    public static ArrayList<String> getStreakStart() 
    {
        if (con != null) 
        {
            try 
            {
                PreparedStatement query = con.prepareStatement("SELECT START_STREAK FROM RECORDS WHERE USER_ID = " + userIndex);
                ResultSet rs = query.executeQuery();

                while(rs.next())
                {
                    streakStartList.add(rs.getString("START_STREAK"));
                }

                rs.close();
                query.close();
                
                return streakStartList;
            } 
            catch (SQLException sqle) 
            {
                sqle.printStackTrace();
            } 
        }
        return null;
    }
        
    public static ArrayList<String> getStreakEnd() 
    {
        if (con != null) 
        {
            try 
            {
                PreparedStatement query = con.prepareStatement("SELECT END_STREAK FROM RECORDS WHERE USER_ID = " + userIndex);
                ResultSet rs = query.executeQuery();

                while(rs.next())
                {
                    streakEndList.add(rs.getString("END_STREAK"));
                }

                rs.close();
                query.close();
                
                return streakEndList;
            } 
            catch (SQLException sqle) 
            {
                sqle.printStackTrace();
            } 
        }
        return null;
    }
            
    public static ArrayList<Integer> getDays() 
    {
        if (con != null) 
        {
            try 
            {
                PreparedStatement query = con.prepareStatement("SELECT DAYS FROM RECORDS WHERE USER_ID = " + userIndex);
                ResultSet rs = query.executeQuery();

                while(rs.next())
                {
                    daysList.add(rs.getInt("DAYS"));
                }

                rs.close();
                query.close();
                
                return daysList;
            } 
            catch (SQLException sqle) 
            {
                sqle.printStackTrace();
            } 
        }
        return null;
    }
                
}
