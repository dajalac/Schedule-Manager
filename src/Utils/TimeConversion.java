/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

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
        
        public  String toLocal (LocalDate date , String hour, String minutes){
            
           LocalDateTime ldt = LocalDateTime.of(date.getYear(),
                   date.getMonthValue(),
                   date.getDayOfMonth(), 
                   Integer.parseInt(hour), 
                   Integer.parseInt(minutes));
           DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
           
           return customFormatter.format(ldt); 
            
            
        }
        public static String toUtc(LocalDateTime ldt){
            ZonedDateTime locZdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
            ZonedDateTime utcZdt = locZdt.withZoneSameInstant(ZoneOffset.UTC);
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            return customFormatter.format(utcZdt);      
        }
        
        
        
   // }
}
