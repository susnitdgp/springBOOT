package com.kaniha.commonspare.controllers;


import java.io.BufferedInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaniha.activity.model.ProcessModel;
import com.kaniha.auth.utility.UserInfo;
import com.kaniha.commonspare.entity.GroupMaterial;
import com.kaniha.commonspare.entity.GroupMaterialPK;
import com.kaniha.commonspare.model.GroupMaterialPager;
import com.kaniha.commonspare.repository.GroupMaterialRepository;
import com.kaniha.commonspare.service.CommonspareService;

@Controller
public class CommonspareController {
	
	@Autowired
	CommonspareService commonspareService;
	
	@Autowired
	GroupMaterialRepository groupMaterialRepository;
	
	@RequestMapping(value={"/commonspare/home"},method=RequestMethod.GET)
	public String home(Model model){
		
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		
		if(auth!=null){
			
			
		}
		
		List<ProcessModel> lpm=commonspareService.getListOfCommonSpareWorkflow();
		
		ProcessModel process=lpm.get(0);
		
		model.addAttribute("process_id",  process.getId());
		
		model.addAttribute("listProcess", lpm);
		
		model.addAttribute("process_exec_base_url", "/commonspare/startProcess");
		
		model.addAttribute("pending_task_count",commonspareService.countPendingTaskForGroup("CS_MTP") );

		return "commonspare/home";

	}
	
	@RequestMapping(value={"/commonspare/startProcess"},method=RequestMethod.GET)
	public String startProcess(@RequestParam(value="processId",
			required=true)String processId){
		
		Map<String,Object> process_variables=new HashMap<String,Object>();
		
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		
		if(auth!=null){
			
			UserInfo user=(UserInfo) auth.getPrincipal();
			process_variables.put("initiator", user.getEmp_num());
		}
			
		
		process_variables.put("for_year", "2016");
		
		
		commonspareService.startProcessById(processId, process_variables);
		
		return "redirect:/commonspare/home";
		
	}
	
	@RequestMapping(value={"/commonspare/pendingTaskList"},method=RequestMethod.GET)
	public String pendingTaskList(Model model){
		
		model.addAttribute("task_exec_base_url", "/commonspare/tastExecute");
		model.addAttribute("pending_task_count",commonspareService.countPendingTaskForGroup("CS_MTP") );
		model.addAttribute("tasks", commonspareService.getTasksForGroup("CS_MTP"));
		
		return "commonspare/pendingTaskList";
	}
	
	@RequestMapping(value={"/commonspare/mtpTask1"},method=RequestMethod.GET)
	public String mtpTask1(
			@RequestParam(value="pageNumber",required=false,defaultValue="1")int pageNumber,
			@RequestParam(value="sortColumn",required=false,defaultValue="id")String sortColumn,
			@RequestParam(value="sortOrder",required=false,defaultValue="ASC")String sortOrder,
			Model model){
		
		int PAGE_SIZE=10;
		Pageable pageable=null;
		
		if(sortOrder.equals("ASC")){
			
			pageable=new PageRequest(pageNumber-1,PAGE_SIZE);
			
		}else{
			
			pageable=new PageRequest(pageNumber-1,PAGE_SIZE);
		}
		
		Page<GroupMaterial> page=groupMaterialRepository.getAllGroupMaterialForYear("2016",pageable);
		
		//Page<GroupMaterial> page=new PageImpl<GroupMaterial>(temp_list,pageable,temp_list.size());
		
		
		//Pager UI Code
		GroupMaterialPager pager=new GroupMaterialPager();
		
		int current=page.getNumber() + 1;;
		pager.setCurrentIndex(current);
		
		pager.setBaseUrl("/commonspare/mtpTask1");
		
		int begin=Math.max(1, current - 5);
		pager.setBeginIndex(begin);
		int end= Math.min(begin + 10, page.getTotalPages());
		pager.setEndIndex(end);
		pager.setTotalPageCount(page.getTotalPages());
		
		//Page UI Code
		model.addAttribute("pager", pager);
		model.addAttribute("groupMats", page.getContent());
		model.addAttribute("edit_popup_url", "/commonspare/editGroupMaterial");
		
		return "commonspare/mtptask1";
	}
	
	@RequestMapping(value={"/commonspare/editGroupMaterial"},method=RequestMethod.GET)
	public String editGroupMaterial(
			@RequestParam(value="forYear",required=true)String forYear,
			@RequestParam(value="matCode",required=true)String matCode,
			@RequestParam(value="groupCode",required=true)String groupCode,Model model){
		
		GroupMaterialPK pk=new GroupMaterialPK(groupCode,matCode,forYear);
		GroupMaterial gm=groupMaterialRepository.findOne(pk);
		
		model.addAttribute("gm", gm);
		return "commonspare/editGroupMaterial";
		
	}
	
	@RequestMapping(value={"/commonspare/getCSProcessImage"},method=RequestMethod.GET)
	public void getCSProcessImage(@RequestParam(value="process_id",
			required=true)String process_id,HttpServletResponse response){

		    InputStream in = commonspareService.getCSProcessDiagram(process_id);		   
		    response.reset();
		    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		    try {
				response.setContentLength(in.available());
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		    
		    final BufferedInputStream bin = new BufferedInputStream(in);
		    
		    try {
	            FileCopyUtils.copy(bin, response.getOutputStream());
	            response.flushBuffer();
	        } catch (final IOException e) {
	            
	            e.printStackTrace();
	        }
		
	}
	
	@RequestMapping(value={"/commonspare/getCSTaskImage"},method=RequestMethod.GET)
	public void getCSTaskImage(HttpServletResponse response){

		    InputStream in = commonspareService.getActiveTaskDiagram("commonSpareTaskProcess:7:70018");		   
		    response.reset();
		    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		    try {
				response.setContentLength(in.available());
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		    
		    final BufferedInputStream bin = new BufferedInputStream(in);
		    
		    try {
	            FileCopyUtils.copy(bin, response.getOutputStream());
	            response.flushBuffer();
	        } catch (final IOException e) {
	            
	            e.printStackTrace();
	        }
		
	}
	

}
