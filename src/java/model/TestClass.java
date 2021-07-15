package model;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TestClass {
    public static void main(String[] args) 
    {     
//        long millis = System.currentTimeMillis();
//        System.out.println(millis + " This is the first time!");
        String sampleTime = "2021-12-21 18:50:23";
        Timestamp date = Timestamp.valueOf(sampleTime);
        long millis = date.getTime();
        System.out.println(millis + " This is the first time!");
        System.out.println(date.toString());
        
        wait(1000);
        
//        long millis2 = System.currentTimeMillis();
        
        String sampleTime2 = "2021-12-21 21:23:12";
//        Timestamp date2 = new Timestamp(millis2);
        Timestamp date2 = Timestamp.valueOf(sampleTime2);
        long millis2 = date2.getTime();
        System.out.println(millis2 + " This is the second time!");
        System.out.println(date2.toString()); 
        
        long difference = Math.abs(millis2 - millis);
//        System.out.println(difference);

        // First Way
//        Duration duration = Duration.ofMillis(difference);
//        long days = duration.toDays();

//Second Way
        long days = TimeUnit.MILLISECONDS.toDays(difference);
        long hours = TimeUnit.MILLISECONDS.toHours(difference);
        
        System.out.println(days);
        System.out.println(hours);
        
//        Records records = new Records();
//        ArrayList<Integer> attempts = records.getAttempts();
//        ArrayList<String> streakStart = records.getStreakStart();
//        ArrayList<String> streakEnd = records.getStreakEnd();
//        ArrayList<Integer> daysList = records.getDays();
        
//        System.out.println(attempts.size());
//        System.out.println(streakStart.size());
//        System.out.println(streakEnd.size());
//        System.out.println(days.size());
        
//        records.close();
        
//        System.out.println(attempts.get(0));
//        System.out.println(streakStart.get(0));
//        System.out.println(streakEnd.get(0));
//        System.out.println(daysList.get(0));
        
        
        
    }
    
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}
