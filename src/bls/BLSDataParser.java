/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bls;


import java.io.PrintWriter;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Iterator;
import java.sql.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 *
 * @author Archana
 */
public class BLSDataParser {
    
    public void parseData()
    {
        Connection conn = null;
        Statement stmt = null;
        try
        {
            
            String DBUserName = "BLSuser";
            String DBPassword = "Password123";

            String DBUrl = "jdbc:mysql://localhost:3306/BLSData?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
            Class.forName("com.mysql.cj.jdbc.Driver");

             //Open a connection
             System.out.println("- Connecting to database...");
             conn = DriverManager.getConnection(DBUrl, DBUserName, DBPassword);

            System.out.println("- Connected to database successfully...");

            //Execute a query
            System.out.println("- Inserting records into the table...");
            stmt = conn.createStatement();


            Object obj = new JSONParser().parse(new FileReader("BLSoutput.json"));
            PrintWriter outputfile = new PrintWriter("BLSparser.txt");
            // typecasting obj to JSONObject
            JSONObject jo = (JSONObject) obj; 

            // getting status
            //System.out.println("BLS Data Parsed Output");
            String status = (String) jo.get("status");
            System.out.println("-- Request status:" + status);

            // getting responseTime
            long responseTime = (long) jo.get("responseTime");
            System.out.println("-- Request responseTime:"+ responseTime); 
            
            // getting message
            JSONArray message =  (JSONArray)jo.get("message");
            //System.out.println("-- Message:"+ message.get(0)); 

            // Creating JSON object for Results
            JSONObject Results =  (JSONObject)jo.get("Results");

            // Creating JSON array object for Series
            JSONArray series = (JSONArray) Results.get("series");

            Iterator itr = series.iterator();
            while (itr.hasNext()) 
            {
                Object ItrSeriesObject = itr.next();
                JSONObject jsonObject2 = (JSONObject) ItrSeriesObject;
                String seriesID = (String)jsonObject2.get("seriesID");
                System.out.println("\t\tseriesID:" + seriesID);

                String level0 = "'"+seriesID+"'";

                JSONArray data = (JSONArray)jsonObject2.get("data");

                Iterator itrData = data.iterator();
                while (itrData.hasNext()) 
                {
                    Object dataIterObject = itrData.next();
                    JSONObject jsonObject3 = (JSONObject) dataIterObject;

                    String period = (String)jsonObject3.get("period");
                    //System.out.println("\t\tperiod:" + period);

                    String year = (String)jsonObject3.get("year");
                    //System.out.println("\t\tyear:" + year);

                    String periodName = (String)jsonObject3.get("periodName");
                    //System.out.println("\t\tperiodName:" + periodName);

                    String value = (String)jsonObject3.get("value");
                    //System.out.println("\t\tvalue:" + value);

                    JSONArray footnotes = (JSONArray)jsonObject3.get("footnotes");
                    //System.out.println("data:" + footnotes);

                    String latest = (String)jsonObject3.get("latest");
                    //System.out.println("\t\tlatest:" + latest);

                    String level1 = "'"+period+"',"+year+",'"+periodName+"',"+value+","+latest;

                    Iterator itrFootNotes = footnotes.iterator();
                    
                    while(itrFootNotes.hasNext())
                    {
                        Object footNotesIterObject = itrFootNotes.next();
                        JSONObject jsonObject4 = (JSONObject)footNotesIterObject;

                        String code = (String)jsonObject4.get("code");
                        //System.out.println("\t\t\tcode:" + code);

                        String text = (String)jsonObject4.get("text");
                        //System.out.println("\t\t\ttext:" + text);

                        String level2 = (level0+","+level1+",'"+code+"','"+text+"'");

                        outputfile.println(level2); 

                        String sql = "INSERT INTO seriesData VALUES " + "(" + level2 + ")";
                        stmt.executeUpdate(sql);
                    }
                }

            }
            outputfile.close(); 
            System.out.println("");
        }
        catch(Exception e)
        {
            System.out.println("");
            System.out.println("ERROR - Exception from BLSDataParser:"+e.getMessage());
        }
        finally
        {
            //finally block used to close resources
            try
            {
               if(stmt!=null)
                  conn.close();
            }
            catch(SQLException se)
            {
                System.out.println("");
                System.out.println("ERROR - SQLException:"+ se.getMessage());
            }
            try
            {
               if(conn!=null)
                  conn.close();
            }
            catch(SQLException se)
            {
               se.printStackTrace();
            }
        }//end finally try
    }
}
