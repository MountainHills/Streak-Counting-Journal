package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Records {
    private static ArrayList<Integer> attemptsList = new ArrayList<>();
    private static ArrayList<String> streakStartList = new ArrayList<>();
    private static ArrayList<String> streakEndList = new ArrayList<>();
    private static ArrayList<Integer> daysList = new ArrayList<>();
    private static ArrayList<Boolean> isHoursList = new ArrayList<>();
    
    // Setters
    public static void setRecords(ResultSet rs) throws SQLException
    {
        while (rs.next())
        {
            // Debugging Purposes.
            System.out.println("The result set is not empty.");
		
	    attemptsList.add(rs.getInt("ATTEMPT"));
            streakStartList.add(rs.getString("START_STREAK"));
            streakEndList.add(rs.getString("END_STREAK"));
            daysList.add(rs.getInt("DAYS"));
            isHoursList.add(rs.getBoolean("IS_HOURS"));
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
    
    public static ArrayList<Boolean> getIsHours()
    {
        return isHoursList;  
    }
    
    public static void resetData()
    {
        attemptsList.clear();
        streakStartList.clear();
        streakEndList.clear();
        daysList.clear();
        isHoursList.clear();
    }
}
