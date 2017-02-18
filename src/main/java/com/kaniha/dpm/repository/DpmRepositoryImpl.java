package com.kaniha.dpm.repository;



import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kaniha.dpm.model.Content;
import com.kaniha.dpm.model.ContentFileTypeComparer;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;


@Repository
public class DpmRepositoryImpl implements DpmRepository {
	
	private static final Logger log = LoggerFactory.getLogger(DpmRepositoryImpl.class);

	@Override
	public List<Content> getAllFiles(String directoryName) {
		
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("ntpc.orgn", "tstps.admin", "aDk@n1h@8691");
		
		List<Content> resultList = new ArrayList<Content>();
		
		try{
			
		
		
		SmbFile f = new SmbFile (directoryName,auth);
		
		this.getAllVFS(directoryName);
		
		
		
		 SmbFile[] fList = f.listFiles();
		 
		
		 int i=1;
		 
		 SimpleDateFormat formatter= new SimpleDateFormat("dd-MMM-yy [HH:mm]");
		 formatter.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		 
		 
		 
		 	for (SmbFile file : fList) {
		 		
	           
			 if (file.isDirectory()) {
	                
	            	Content c=new Content();
	            	c.setSerial(i);
	            	c.setFolder(true);
	            	c.setFile(false);
	            	c.setName(file.getName());
	            	c.setUrl(file.getPath());
	            	c.setExtension("FOLDER");
	            	
	            	
	            	Date creationDate=new Date(file.getDate());
	            	c.setCreationTime(formatter.format(creationDate));
	            	
	            	//log.info(file.getName());
	            	//log.info(file.getParent());
	            	
	          
	                resultList.add(c);
	            	
	            }
	            else if (file.isFile()) {
	           
	                Content c=new Content();
	                c.setSerial(i);
	            	c.setName(file.getName());
	            	
	            	c.setSize(file.length());
	            	c.setUrl(file.getPath());
	            	
	            	c.setFile(true);
	            	c.setFolder(false);
	            	c.setExtension(FilenameUtils.getExtension(file.getName()));
	            	
	            	Date creationDate=new Date(file.getDate());
	            	c.setCreationTime(formatter.format(creationDate));
	            	
	            	resultList.add(c);
	            }
			 
			 i=i+1;
	        }
		 	
		 	Collections.sort(resultList,new ContentFileTypeComparer());
		 	
		}catch (SmbException e) {
			
			e.printStackTrace();
		}
		catch (MalformedURLException e1) {
			
			e1.printStackTrace();
		}
		
		 
		
		
		return resultList;
	}

	@Override
	public void recursiveParent(String directoryName) {
		
		
		SmbFile f;
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("ntpc.orgn", "tstps.admin", "aDk@n1h@8691");
		
		try {
			
			f = new SmbFile (directoryName,auth);
			if(f.isDirectory()){
				
				log.info(f.getPath());
				
				recursiveParent(f.getParent());
			}
			
		}catch (SmbException e) {
			
			e.printStackTrace();
		}
		catch (MalformedURLException e1) {
			
			e1.printStackTrace();
		}
		
	}
	
	public void getAllVFS(String directoryName){
		
		String url = "smb://10.0.120.240/dept-file-repos/OPERATION_DEPT/";
		//url=directoryName;
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("ntpc.orgn", "tstps.admin", "aDk@n1h@8691");
		SmbFile dir;
		try {
			
			dir = new SmbFile(url, auth);
			
				for (SmbFile f : dir.listFiles())
				{
				    System.out.println("SMB:" + f.getName() + "Time:" + new Date(f.getDate()).toString());
				 
				    
				}
		
			
		}catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		catch (SmbException e) {
			
			e.printStackTrace();
		}
		
		
		
		
	}

}
