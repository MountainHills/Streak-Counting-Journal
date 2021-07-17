package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

public class Streak {
    private static long currentStreak;
    private static boolean isHour = false;
    private static String startTimeStreak;
    private static int currentAttempt;
    private static String bestAttempt;
    private static boolean noRecord = true;
    
    // Setters and Getters
    public static long getCurrentStreak() {
        return currentStreak;
    }
    
    public static boolean isHour() {
        return isHour;
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
        System.out.println("The start time streak is: " + startTimeStreak);
        
        Timestamp startStreak = Timestamp.valueOf(startTimeStreak);
        long startTime = startStreak.getTime();
     
        long sysTime = System.currentTimeMillis();
        
        long difference = Math.abs(sysTime - startTime);
        
        currentStreak = TimeUnit.MILLISECONDS.toDays(difference);

        if (currentStreak == 0) {
            currentStreak = TimeUnit.MILLISECONDS.toHours(difference);
            isHour = true;
            System.out.println("The time is in hours. The hours are: " + currentStreak);
        }
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
            currentAttempt = rs.getInt(1);
        }
    }

    public static void setBestAttempt(ResultSet rs) throws SQLException {
        
        // Gets the values of the first record.
        int attemptNumber = 0;
        String timeValue = "Days";
        int day = 0;
        boolean isDBHour = false;
        
        while (rs.next())
        {
            // Does only one iteration.
            attemptNumber =  rs.getInt("ATTEMPT");
            day = rs.getInt("DAYS");
            isDBHour = rs.getBoolean("IS_HOURS");
            break;
        }
        
        if (isDBHour) timeValue = "Hours";

        bestAttempt = String.format("Best: Attempt # %s: %s %s", attemptNumber, day, timeValue);
        
        // The first attempt is the best attempt.
        if (attemptNumber == 1) 
        {
            if (isHour) timeValue = "Hours"; 
            bestAttempt = String.format("Best: Attempt # %s: %s %s", attemptNumber, currentStreak, timeValue);
        }
        
        System.out.println("The best attempt is: " + bestAttempt);
        System.out.println("Attempt Number: " + attemptNumber + "; Day: " + day + "; Time Value: " + timeValue);
        
    }

    public static boolean isEmpty(ResultSet rs) throws SQLException {
        if (rs.next())
        {
            int numberOfRecords = rs.getInt(1);
            noRecord = numberOfRecords == 0;
            System.out.println("Is the streak empty? : " + noRecord);
            return noRecord;
        }
        return true;
    }
}
