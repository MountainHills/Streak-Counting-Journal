package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Streak {
    private static int currentStreak;
    private static String startTimeStreak;
    private static int currentAttempt;
    private static String bestAttempt;
    
    // Setters and Getters
    public static int getCurrentStreak() {
        return currentStreak;
    }

    public static String getStartTimeStreak() {
        return startTimeStreak;
    }

    public static int getCurrentAttempt() {
        return currentAttempt;
    }

    public static String getBestAttempt() {
        return bestAttempt;
    }

    public static void setCurrentStreak(ResultSet rs) {
        
    }
    
    public static void setStartTimeStreak(ResultSet rs) throws SQLException {
        startTimeStreak = rs.getString("START_STREAK");
    }
    
    public static void setCurrentAttempt(ResultSet rs) throws SQLException {
        int lastAttempt = Integer.parseInt(rs.getString("ATTEMPT"));
        
        currentAttempt = lastAttempt + 1;
    }

    public static void setBestAttempt(ResultSet rs) {
        
    }
    
    public static void clear()
    {
        
    }
    
}
