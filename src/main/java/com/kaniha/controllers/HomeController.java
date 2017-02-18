package com.kaniha.controllers;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.kaniha.activity.service.MyActivityService;



@Controller
public class HomeController {
	
	@Autowired
	MyActivityService myActivityService;
	
	
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	
	
	
	@RequestMapping(value={"/home","/"},method=RequestMethod.GET)
	public String home(@RequestParam(value="name",
		required=false, defaultValue="Susanta")String name, 
		Model model){

		model.addAttribute("tasks", myActivityService.getAllTasks());
		model.addAttribute("task_exec_base_url", "/home/executeTask");
		
		model.addAttribute("process_count", myActivityService.countProcess());
		model.addAttribute("pending_task_count",myActivityService.countPendingTask("009697") );
		model.addAttribute("completed_task_count",myActivityService.countCompletedTask("009697") );
		
		//myActivityService.getTaskData("test");
		
		
		log.info("Inside Home");
		
		
		
		return "home";
		
	}
	@RequestMapping(value={"/test"},method=RequestMethod.GET)
	@Secured("ROLE_GOSWAMI")
	public String test(){
		
		return "test";
		
	}
	
	
	@RequestMapping(value={"/home/listProcess"},method=RequestMethod.GET)
	public String listProcess(Model model){
		
		model.addAttribute("process_exec_base_url", "/home/startProcess");
		
		model.addAttribute("process_count", myActivityService.countProcess());
		model.addAttribute("pending_task_count",myActivityService.countPendingTask("009697") );
		model.addAttribute("completed_task_count",myActivityService.countCompletedTask("009697") );
		
		
		model.addAttribute("listProcess",myActivityService.listProcess());
		
		return "activity/listProcess";
	}
	
	@RequestMapping(value={"/home/startProcess"},method=RequestMethod.GET)
	public String startProcess(@RequestParam(value="processId",
			required=true)String processId){
		
		Map<String,Object> process_variables=new HashMap<String,Object>();
		process_variables.put("initiator", "009697");
		
		myActivityService.startProcessById(processId,process_variables);
		
		return "redirect:/home";
	}
	
	@RequestMapping(value={"/home/getProcessImage"},method=RequestMethod.GET)
	public void getProcessImage(HttpServletResponse response) throws IOException{
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    IOUtils.copy(myActivityService.getProcessDiagram("test"), response.getOutputStream());
		
	}
	
	@RequestMapping(value={"/home/pendingTaskList"},method=RequestMethod.GET)
	public String pendingTaskList(Model model){
		
		model.addAttribute("tasks", myActivityService.getAllTasks());
		model.addAttribute("task_exec_base_url", "/home/executeTask");
		
		model.addAttribute("process_count", myActivityService.countProcess());
		model.addAttribute("pending_task_count",myActivityService.countPendingTask("009697") );
		model.addAttribute("completed_task_count",myActivityService.countCompletedTask("009697") );
		
		return "activity/pendingTaskList";
	}
	
	@RequestMapping(value={"/home/executeTask"},method=RequestMethod.GET)
	public String executeTask(@RequestParam(value="taskId",
			required=true)String taskId,Model model){
		
		Map<String,String> form_properties=new HashMap<String,String>();
		form_properties.put("fname", "susanta");
		form_properties.put("lname", "goswami");
		
		Map<String,Object> process_variables=new HashMap<String,Object>();
		
		myActivityService.submitUserTaskFormData(taskId, form_properties,process_variables);
		
		return "redirect:/home";
				
	}
	
	@RequestMapping(value={"/home/completedTaskList"},method=RequestMethod.GET)
	public String completedTask(Model model){
		
		
		
		return "activity/completedTaskList";
				
	}
	
	@RequestMapping(value={"/home/executeTaskModal"},method=RequestMethod.GET)
	public String executeTaskModal(@RequestParam(value="taskId",
			required=true)String taskId,Model model){
		
		Map<String,String> properties=new HashMap<>();
		properties.put("fname", "susanta");
		properties.put("lname", "goswami");
		
		//myActivityService.submitUserTaskFormData(taskId, properties);
		
		return "activity/executeTaskModal";
				
	}
	

}