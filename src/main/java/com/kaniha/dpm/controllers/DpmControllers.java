package com.kaniha.dpm.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kaniha.dpm.model.Breadcrumb;
import com.kaniha.dpm.model.Content;
import com.kaniha.dpm.model.LeftMenu;
import com.kaniha.dpm.model.Pager;

import com.kaniha.dpm.service.DpmService;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

@Controller
public class DpmControllers {
	
	private static final Logger log = LoggerFactory.getLogger(DpmControllers.class);
	
	@Autowired
	DpmService  dpmService;
	
	@RequestMapping(value="/dpm/BrowseFileSystemHome", method=RequestMethod.GET)
	public String redirectBrowserFileSystem(HttpServletRequest request){
		
		request.getSession().setAttribute("fileList", null);
		
		return "redirect:/dpm/BrowseFileSystem?location=OPN&pageNumber=1";
		
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/dpm/BrowseFileSystem", method=RequestMethod.GET)
	public String browseFileSystem(
			@RequestParam(value="department",required=false,defaultValue="OPN")String department,
			@RequestParam(value="location",required=false,defaultValue="OPN")String location,
			@RequestParam(value="pageNumber",required=false,defaultValue="1")int pageNumber,
			Model model,HttpServletRequest request){
	
		int PRODUCT_LIST_PAGE_SIZE=10;
		
		String rootPath=null;
		
		
		LeftMenu l=new LeftMenu();		
		
		if(location.equals("OPN")){
			
			rootPath="smb://10.0.120.240/dept-file-repos/OPERATION_DEPT/";
			
			l.setForDept("OPN");
			
		}
			
		else if(location.equals("EEMG")){
				
			rootPath="smb://10.0.120.240/dept-file-repos/EEMG_DEPT/";
			
			l.setForDept("EEMG");
	
			
		}else if(location.equals("MTP")){
			
			rootPath="smb://10.0.120.240/dept-file-repos/MTP_DEPT/";
			
			l.setForDept("MTP");
		
		}else if(location.equals("CHP")){
			
			rootPath="smb://10.0.120.240/dept-file-repos/CHP_DEPT/";
			
			l.setForDept("CHP");
		
		}else if(location.equals("CHEM")){
			
			rootPath="smb://10.0.120.240/dept-file-repos/CHEM_DEPT/";
			
			
			l.setForDept("CHEMISTRY");
		
		}else if(location.equals("MGR")){
			
			rootPath="smb://10.0.120.240/dept-file-repos/MGR_DEPT/";
			
			
			l.setForDept("MGR");
		
		}
		else if(location.equals("SAFETY")){
			
			rootPath="smb://10.0.120.240/dept-file-repos/SAFETY_DEPT/";
			
			
			l.setForDept("TS");
		}
		else{
			rootPath=location;
		}
		
		
		//Breadcrumb Population
		List<Breadcrumb> lsb=new ArrayList<Breadcrumb>();
		dpmService.recursiveParent(rootPath,lsb);
		Collections.reverse(lsb);
		
		Breadcrumb b=lsb.get(1);
		log.info(b.getLabel());
		
		if(b.getLabel().equals("SAFETY_DEPT")){
			l.setForDept("SAFETY");
		}
		if(b.getLabel().equals("OPERATION_DEPT")){
			l.setForDept("OPN");
		}
		if(b.getLabel().equals("CHEM_DEPT")){
			l.setForDept("CHEM");
		}
		if(b.getLabel().equals("EEMG_DEPT")){
			l.setForDept("EEMG");
		}
		if(b.getLabel().equals("MTP_DEPT")){
			l.setForDept("MTP");
		}
		if(b.getLabel().equals("CHP_DEPT")){
			l.setForDept("CHP");
		}
		if(b.getLabel().equals("MGR_DEPT")){
			l.setForDept("MGR");
		}
		
		
		//Paged List Population
		PagedListHolder<?> pagedListHolder=new PagedListHolder(dpmService.getAllFiles(rootPath));;
				
		//pagedListHolder=new PagedListHolder(dpmRepository.getAllFiles(rootPath));
		pagedListHolder.setPageSize(PRODUCT_LIST_PAGE_SIZE);
			
		final int goToPage=pageNumber-1;
		if(goToPage <= pagedListHolder.getPageCount() && goToPage >=0){
				pagedListHolder.setPage(goToPage);
		}
			
		
		//Pager Population
		Pager pager=new Pager();
		int current=pagedListHolder.getPage()+1;
		pager.setCurrentIndex(current);
		int begin=Math.max(1, current-PRODUCT_LIST_PAGE_SIZE);
		pager.setBeginIndex(begin);
		int end=Math.min(begin+5, pagedListHolder.getPageCount());
		pager.setEndIndex(end);
		int totalPageCount=pagedListHolder.getPageCount();
		pager.setTotalPageCount(totalPageCount);
		String BASE_URL="/dpm/BrowseFileSystem";
		pager.setBaseUrl(BASE_URL);
		pager.setRootPath(rootPath);
		
		//View Model Population
		model.addAttribute("breadcrumbs", lsb);
		model.addAttribute("leftmenu", l);
		model.addAttribute("pager", pager);
		model.addAttribute("contents", pagedListHolder);
		
		
		return "dpm/browseFileSystem";
	}
	
	@RequestMapping(value="/dpm/download", method=RequestMethod.GET)
	public void downloadFile(@RequestParam(value="path",required=true) String path,HttpServletResponse response) {
	
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("ntpc.orgn", "tstps.admin", "aDk@n1h@8691");
		
		InputStream is;
		
		
		try {
			SmbFile f=new SmbFile(path,auth);
			String fileName=f.getName();
			
			log.info(fileName  + " Downloaded");
			
			response.setHeader("Content-Disposition", "attachment; filename="+fileName);
			response.setContentType("application/octet-stream");
			response.setContentLength((int)f.length());	
			
			is=f.getInputStream();
			
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
			
			is.close();
			
		}catch (SmbException e) {
			
			e.printStackTrace();
		}
		catch (MalformedURLException e1) {
			
			e1.printStackTrace();
		}
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
	
	   
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/dpm/ContentDetails",method=RequestMethod.GET)
    public @ResponseBody Content contentDetails(@RequestParam(value="id", required=true, defaultValue="5")int id,
    		HttpServletRequest request) {
       
		PagedListHolder<?> pagedListHolder=(PagedListHolder<?>)request.getSession().getAttribute("fileList");
		
		if(pagedListHolder != null){
			
			List<Content> lsc=(List<Content>) pagedListHolder.getSource();
			
			Content temp=null;
			
			for(Content c:lsc){
				
				if(c.getSerial() == id){
					temp=c;
				}
			}
					
			return  temp;
			
		}else{
			
			return null;
		}
	
    }
	
	
}
	
	