/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Danielle
 */
public class TimeConversion {
    
    // static class
   // static class TimeConversion{
        
       // combine data and time from the user pick up, the returned vaulue is passed to toUtc 
        public  LocalDateTime dateTimeCombine (LocalDate date , String hour, String minutes){
          
           LocalDateTime ldt = LocalDateTime.of(date.getYear(),
                   date.getMonthValue(),
                   date.getDayOfMonth(), 
                   Integer.parseInt(hour), 
                   Integer.parseInt(minutes));
           //DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
           
           //return customFormatter.format(ldt); 
           return ldt; 

        }
        
        // convert the localDateTime to utc and return a string value
        public static String toUtcString(LocalDateTime ldt){
            ZonedDateTime locZdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
            ZonedDateTime utcZdt = locZdt.withZoneSameInstant(ZoneOffset.UTC);
            System.out.println(utcZdt.toString());
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            return customFormatter.format(utcZdt);      
        }
        
        // value to be stored in db
        public static Timestamp utcToStore(LocalDateTime ldt){
            
            // first, get the date and time choose by the user and convert to UTC
            ZonedDateTime locZdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
            ZonedDateTime utcZdt = locZdt.withZoneSameInstant(ZoneOffset.UTC);
            
            //Second, convert the utc in localdata type
            LocalDateTime localDT = utcZdt.toLocalDateTime();
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            // third, convert in timestamp to sent to db
            
            Timestamp ts = Timestamp.valueOf(customFormatter.format(localDT));
            
            return ts; 
           
        }
        
        public static String utcToLocalTime (Timestamp ts){
            ZoneId zid = ZoneId.systemDefault();
            ZonedDateTime localUtc = ts.toLocalDateTime().atZone(ZoneId.of("UTC"));
           
            ZonedDateTime localdt = localUtc.withZoneSameInstant(zid);

            // format and convert to string 
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            return customFormatter.format(localdt);
        }

}
