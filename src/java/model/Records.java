package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Records {
    private static ArrayList<Integer> attemptsList = new ArrayList<>();
    private static ArrayList<String> streakStartList = new ArrayList<>();
    private static ArrayList<String> streakEndList = new ArrayList<>();
    private static ArrayList<Integer> daysList = new ArrayList<>();
    
    // Setters
    public static void setAttempts(ResultSet rs) throws SQLException 
    {
        try 
        {
            while(rs.next())
            {
                attemptsList.add(rs.getInt("ATTEMPT"));
            }
        } 
        catch (SQLException sqle) 
        {
            sqle.printStackTrace();
        } 
    }
    
    public static void setStreakStart(ResultSet rs) throws SQLException 
    {
        try 
        {
            while(rs.next())
            {
                streakStartList.add(rs.getString("START_STREAK"));
            }
        } 
        catch (SQLException sqle) 
        {
            sqle.printStackTrace();
        } 
    }
    
    public static void setStreakEnd(ResultSet rs) throws SQLException 
    {
        try 
        {
            while(rs.next())
            {
                streakEndList.add(rs.getString("END_STREAK"));
            }
        } 
        catch (SQLException sqle) 
        {
            sqle.printStackTrace();
        } 
    }
    
    public static void setDays(ResultSet rs) throws SQLException 
    {
        try 
        {
            while(rs.next())
            {
                daysList.add(rs.getInt("DAYS"));
            }
        } 
        catch (SQLException sqle) 
        {
            sqle.printStackTrace();
        } 
    }
    
    // Getters
    public static ArrayList<Integer> getAttempts()
    {
        return attemptsList;  
    }
    
    public static ArrayList<String> getStreakStart()
    {
        return streakStartList;  
    }
    
    public static ArrayList<String> getStreakEnd()
    {
        return streakEndList;  
    }
    
    public static ArrayList<Integer> getDays()
    {
        return daysList;  
    }
    
    public static void resetData()
    {
        attemptsList.clear();
        streakStartList.clear();
        streakEndList.clear();
        daysList.clear();
    }
}
