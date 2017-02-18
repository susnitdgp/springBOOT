package com.kaniha.commonspare.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaniha.activity.model.ProcessModel;
import com.kaniha.activity.model.TaskModel;

@Service
public class CommonspareService {
	
	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
    
    
    private static final Logger log = LoggerFactory.getLogger(CommonspareService.class);
    
    @Transactional
    public List<ProcessModel> getListOfCommonSpareWorkflow(){
    	List<ProcessModel> lsm=new ArrayList<ProcessModel>();
    	
    	log.info("Querying common spare process list");
    	
    	//List<ProcessDefinition> pdn=repositoryService.createProcessDefinitionQuery().
    	//		 processDefinitionCategory("CommonSpare").
    	//		 orderByProcessDefinitionVersion().desc().list();
    	
    	List<ProcessDefinition> pdn=repositoryService.createProcessDefinitionQuery().
   			 processDefinitionCategory("CommonSpare").latestVersion().list();
   			 
    	
    	 for(ProcessDefinition p:pdn){
    		 ProcessModel pm=new ProcessModel();
    		 
    		 pm.setId(p.getId());
    		 pm.setKey(p.getKey());
    		 pm.setName(p.getName());
    		 pm.setDescription(p.getDescription());
    		 pm.setVersion(p.getVersion());
    		 
    		 lsm.add(pm);
    	 }
    	 
    	 return lsm;
    	
    }
    
    @Transactional
    public void startProcessById(String processId,Map<String,Object> process_variables){
    	
    	log.info("starting process by id");
    	
    	
    	runtimeService.startProcessInstanceById(processId,process_variables);
     	
    }

    @Transactional
    public int countPendingTaskForGroup(String taskCandidateGroup){
    	
    	log.info("counting pending task list");
    	
    	List<String> processCategory=new ArrayList<String>();
    	processCategory.add("CommonSpare");
    	
    	return taskService.createTaskQuery()
    			.processCategoryIn(processCategory)
    			.taskCandidateGroup(taskCandidateGroup).list().size();
    }
    
    @Transactional
    public List<TaskModel> getTasksForGroup(String taskCandidateGroup) {
    	List<TaskModel> lstm=new ArrayList<TaskModel>();
    	
    	List<String> processCategory=new ArrayList<String>();
    	processCategory.add("CommonSpare");
    	
        List<Task> tasks=taskService.createTaskQuery()
        		.processCategoryIn(processCategory)
        		.taskCandidateGroup(taskCandidateGroup).list();
        		
        
        for(Task task:tasks){
        	TaskModel model=new TaskModel();
        	model.setId(task.getId());
        	model.setName(task.getName());
        	model.setCreateTime(task.getCreateTime());
        	model.setProcessId(task.getProcessDefinitionId());
        	lstm.add(model);
        	
        }
        
        return lstm;
    }
    
    
    @Transactional
    public  InputStream getCSProcessDiagram(String processDefinitionId){

    	ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
    			.processDefinitionId(processDefinitionId).singleResult();
    	
    	String diagramResourceName = processDefinition.getDiagramResourceName();
    	
    	InputStream imageStream = repositoryService.
    			getResourceAsStream(processDefinition.getDeploymentId(), diagramResourceName);
    	    	    	
    	return imageStream;
    	
    }
    
    @Transactional
    public InputStream getActiveTaskDiagram(String processDefinitionId){
    	
    	ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
    			.processDefinitionId(processDefinitionId).singleResult();
    	
    	ProcessInstance processInstance=runtimeService.createProcessInstanceQuery()
    			.processDefinitionId(processDefinitionId).singleResult();
    	
    	List<String> highLightedActivities=runtimeService.getActiveActivityIds(processInstance.getBusinessKey());
    	
    	repositoryService.getBpmnModel(processDefinitionId);
    	
    	//List<String> highLightedActivities = new ArrayList<String>();
    	
    	highLightedActivities.add("mtp_task");
    	
    	DefaultProcessDiagramGenerator generator=new DefaultProcessDiagramGenerator();
    	
    	InputStream imageStream=generator.generateDiagram(repositoryService.getBpmnModel(processDefinitionId), "png", highLightedActivities, 0.5);
    	
    	return imageStream;
    }
    

}
