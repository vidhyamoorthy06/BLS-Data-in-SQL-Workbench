/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bls;

import bls.BLSDataExtractor.*;
import bls.BLSDataParser.*;
import java.util.Calendar;
import java.util.Date;
/**
 *
 * @author Archana
 */
public class BLS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try
        {
            // Print Start Time
            Calendar startCalendar = Calendar.getInstance();
            Date startDateTime = startCalendar.getTime();
            System.out.println("Program Start Time " + startCalendar.getTime());

            // 
            BLSDataExtractor objBLSDataExtractor = new BLSDataExtractor();
            objBLSDataExtractor.getData();

            BLSDataParser objBLSDataParser = new BLSDataParser();
            objBLSDataParser.parseData();
            
            // Print End Time and Duration
            Calendar endCalendar = Calendar.getInstance();
            Date endDateTime = endCalendar.getTime();
            
            System.out.println("");
            System.out.println("Program End Time " + endCalendar.getTime());
            long duration = endDateTime.getTime() - startDateTime.getTime();
            System.out.println("Total Duration: " + duration/1000 + " seconds");
        }
        catch(Exception e)
        {
            System.out.println("");
            System.out.println("ERROR - Exception from main:"+e.getMessage() );      
        }
        
    }
    
}
