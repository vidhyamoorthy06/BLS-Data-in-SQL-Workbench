/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bls;

import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import javax.print.attribute.standard.DateTimeAtCompleted;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author Archana
 */
public class BLSDataExtractor {
    public void getData()
    {
        try
        {
            Calendar startCalendar = Calendar.getInstance();
            Date startDateTime = startCalendar.getTime();
            
            System.out.println("- Data Extraction Started from https://api.bls.gov/publicAPI/v2/timeseries/data/ at " +startCalendar.getTime() );
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("https://api.bls.gov/publicAPI/v2/timeseries/data/");
            
            String SeriesID1 = "LAUMT481910000000006";
            String SeriesID2 = "LAUMT481910000000005";
            String SeriesID3 = "LAUMT481910000000004";
            String SeriesID4 = "LAUMT481910000000003";
                        
            StringEntity input = new StringEntity("{\"seriesid\":[\"LAUMT481910000000006\",\"LAUMT481910000000005\",\"LAUMT481910000000004\",\"LAUMT481910000000003\"]}");
            input.setContentType("application/json");
            httpPost.setEntity(input);
            HttpResponse response = httpClient.execute(httpPost);
            
            //variable = response.getEntity().getContent()
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            //System.out.println(responseString);
            
            //create a file first    
            PrintWriter outputfile = new PrintWriter("BLSoutput.json");
            outputfile.print(responseString);
            outputfile.close();   
            
            Calendar endCalendar = Calendar.getInstance();
            Date endDateTime = endCalendar.getTime();

            System.out.println("- Data Extraction Completed at " + endCalendar.getTime());
            
            long duration = endDateTime.getTime() - startDateTime.getTime();

            System.out.println("- Time took for Extraction: " + duration/1000 + " seconds");
            System.out.println("");
        }

        catch(IOException | ParseException e)
        {
            System.out.println("");
            System.out.println("ERROR - Exception from BLSDataExtractor:" + e.getMessage());
        }
    }
}
