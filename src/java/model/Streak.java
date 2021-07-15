package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class Streak {
    private static long currentStreak;
    private static String startTimeStreak;
    private static int currentAttempt;
    private static String bestAttempt;
    private static boolean noRecord = true;
    
    // Setters and Getters
    public static long getCurrentStreak() {
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
    
    public static boolean isRecordEmpty() {
        return noRecord;
    }

    public static void setCurrentStreak() {
        Timestamp startStreak = Timestamp.valueOf(startTimeStreak);
        long startTime = startStreak.getTime();
     
        long sysTime = System.currentTimeMillis();
        
        long difference = Math.abs(sysTime - startTime);
        System.out.println(difference);

        currentStreak = TimeUnit.MILLISECONDS.toDays(difference);
    }
    
    public static void setStartTimeStreak(ResultSet rs) throws SQLException {
        if (rs.next())
        {
            startTimeStreak = rs.getString("START_STREAK");
        }
    }
    
    public static void setCurrentAttempt(ResultSet rs) throws SQLException {
        if (rs.next())
        {
            currentAttempt = rs.getInt(1) + 1;
        }
    }

    public static void setBestAttempt(ResultSet rs) throws SQLException {
        
        // Gets the values of the first record.
        int attemptNumber = 0;
        String timeValue = "Days";
        String day = "0";
        boolean isHour = false;
        
        while (rs.next())
        {
            attemptNumber =  rs.getInt("ATTEMPT");
            day = rs.getString("DAYS");
            isHour = rs.getBoolean("IS_HOURS");
        }
        
        if (isHour) timeValue = "Hours";

        bestAttempt = String.format("Best: Attempt # %s: %s %s", attemptNumber, day, timeValue);
    }

    public static boolean isEmpty(ResultSet rs) throws SQLException {
        if (rs.next())
        {
            int numberOfRecords = rs.getInt(1);
            noRecord = numberOfRecords == 0;
            return noRecord;
        }
        return true;
    }
}
