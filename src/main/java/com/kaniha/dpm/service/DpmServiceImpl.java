package com.kaniha.dpm.service;


import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaniha.dpm.model.Breadcrumb;
import com.kaniha.dpm.model.Content;
import com.kaniha.dpm.repository.DpmRepository;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;


@Service
public class DpmServiceImpl implements DpmService {

	private static final Logger log = LoggerFactory.getLogger(DpmServiceImpl.class);
	
	@Autowired
	DpmRepository  dpmRepository;
	
		
	@Override
	public List<Content> getAllFiles(String directoryName) {
		
		return dpmRepository.getAllFiles(directoryName);
	}

	@Override
	public void recursiveParent(String directoryName,List<Breadcrumb> lsb) {
		
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("ntpc.orgn", "tstps.admin", "aDk@n1h@8691");
		
		SmbFile f;
		
				
		try {
			
			f = new SmbFile (directoryName,auth);
			if(f.isDirectory() && !directoryName.equals("smb://10.0.120.240/")){
				
				Breadcrumb b=new Breadcrumb();
				b.setLabel(f.getName());
				b.setUrl(f.getPath());
				
				lsb.add(b);
				log.info(f.getParent());;
				log.info("DpmService recursiveParent() Called");
				
				recursiveParent(f.getParent(),lsb);
			}
		} catch (SmbException e) {
			
			e.printStackTrace();
		}
		catch (MalformedURLException e1) {
			
			e1.printStackTrace();
		}
		
	}

	

}
