package com.kaniha.scheduler;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.Date;

//import org.apache.commons.math3.util.Precision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.kaniha.controllers.AaqmsData;


@Component
public class ScheduledTask {
	
	@Autowired
	RestTemplate restTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(initialDelay=1000,fixedRate = 600000)
    public void uploadEnvironmentDataToFireBase() {
        System.out.println("The time is now: " + dateFormat.format(new Date()));
        
      
        	//Generated from Firebase Project Settings (*******IMPORTANT********)
        	String firebase_token = "PAKRKMyhx3CBCEhnPXRC11Jjs8oHUsL1yjZCNUZx";
        	
      		
      		//Using RestTemplate for FireBase Data Upload (Using PUT Request)
      		
      		try{
      			
      			final String uriHospital = String.format("https://ntpc-tstps.firebaseio.com/DISPLAY_PAGE/ENV_DATA/AAQMS/HOSPITAL.json?auth=%s",firebase_token);
      			final String uriRwph = String.format("https://ntpc-tstps.firebaseio.com/DISPLAY_PAGE/ENV_DATA/AAQMS/RWPH.json?auth=%s",firebase_token);
      			final String uriAwrs = String.format("https://ntpc-tstps.firebaseio.com/DISPLAY_PAGE/ENV_DATA/AAQMS/AWRS.json?auth=%s",firebase_token);
      			final String uriAdmin = String.format("https://ntpc-tstps.firebaseio.com/DISPLAY_PAGE/ENV_DATA/AAQMS/ADMIN_BUILDING.json?auth=%s",firebase_token);
      			
      			
      			String[] data=this.readAaqmsData("5");
      			
      			log.info("UPLOAD START FOR DATE:" + data[0]);
      			
      			AaqmsData hospital=new AaqmsData();
      			
      			hospital.setLastUpdate(data[0]);
      			hospital.setSOX(data[1]);
      			hospital.setNOX(data[2]);
      			hospital.setPM10(data[3]);
      			hospital.setPM25(data[4]);
      			hospital.setCO2(data[5]);
      			hospital.setO3(data[6]);
      			hospital.setCO(data[7]);
      			
      			
      			restTemplate.put(uriHospital, hospital);
      			log.info("Hospital Data Uploaded");
      			
      			AaqmsData rwph=new AaqmsData();
      			
      			rwph.setSOX(data[8]);
      			rwph.setNOX(data[9]);
      			rwph.setPM10(data[10]);
      			rwph.setPM25(data[11]);
      			rwph.setCO2(data[12]);
      			rwph.setO3(data[13]);
      			rwph.setCO(data[14]);
      			
      			restTemplate.put(uriRwph, rwph);
      			log.info("RWPH Data Uploaded");
      			   			
      			
      			AaqmsData awrs=new AaqmsData();
      			awrs.setSOX(data[15]);
      			awrs.setNOX(data[16]);
      			awrs.setPM10(data[17]);
      			awrs.setPM25(data[18]);
      			awrs.setCO2(data[19]);
      			awrs.setO3(data[20]);
      			awrs.setCO(data[21]);
      			
      			restTemplate.put(uriAwrs, awrs);
      			log.info("AWRS Data Uploaded");
      			
      			
      			AaqmsData admin=new AaqmsData();
      			admin.setSOX(data[22]);
      			admin.setNOX(data[23]);
      			admin.setPM10(data[24]);
      			admin.setPM25(data[25]);
      			admin.setCO2(data[26]);
      			admin.setO3("N/A");
      			admin.setCO(data[27]);
      			
      			restTemplate.put(uriAdmin, admin);
      			log.info("ADMIN Building Data Uploaded");
      			
      			      			      			
      		}
      		catch(RestClientException ex){
      			
      			log.info(ex.getMessage());
      		}
      		catch(Exception ex){
      			
      			log.info(ex.getMessage());
      		}
      		
      	
    }
    

	private String[] readAaqmsData(String rowId){
    	
    	//Excel Read Start from AAQMS WEB SERVER
    	boolean flag1=false;
    	
    	String result[]=null;
    	BufferedReader br=null;
    	String line = "";
    	String cvsSplitBy = ",";
    	
    	String[] linePart1=null;
    	String[] linePart2=null;
		
  		URL url;
  		try {
  			
  			String part2=java.net.URLEncoder.encode("NTPC Kaniha Hourly Report.csv", "UTF-8").replace("+", "%20");
  			url = new URL("http://10.0.124.229/"+part2);
  			InputStream in=url.openStream();
  			br = new BufferedReader(new InputStreamReader(in));
  			int lineCount=1;
  			
  			 while ((line = br.readLine()) != null) {

  				log.info(lineCount + "# "+ line);
  				
  				if(lineCount==5){
  				
  	                 String[] linePart = line.split(cvsSplitBy);
  	                 
  	                 if( !linePart[1].equals("NoData")){
  	                	 
  	                	linePart1=linePart;
  	                	flag1=true;
  	                	
  	                 }
  	                 
  	              
  				}
  				if(lineCount==6){
  	  				
 	                 String[] linePart = line.split(cvsSplitBy);
 	               
 	                 linePart2=linePart;        	
 	                
 				}
  				
  				if(flag1){
  					result=linePart1;
  				}else{
  					result=linePart2;
  				}
  			
  				lineCount=lineCount+1;
                 
                if(lineCount>10){
                	break;
                }

             }
  			
  			
  			
  		}catch(IOException ex){
  			
  			log.info(ex.getMessage());
  		
  		}
  		catch(Exception ex){
  			
  			log.info(ex.getMessage());
  			
  		}finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                	log.info(ex.getMessage());
                }
            }
        }
  		
  		
  		return result;
  			
  			
    }

}
