package com.kaniha.activity.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
//import org.activiti.image.ProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaniha.activity.model.ProcessModel;
import com.kaniha.activity.model.TaskModel;

@Service
public class MyActivityService {
	
	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private FormService formService;
    
    @Autowired
    private HistoryService historyService;
    
    @Autowired IdentityService identityService;
    
    
    private static final Logger log = LoggerFactory.getLogger(MyActivityService.class);
    
    @Transactional
    public long countProcess(){
    	
    	return repositoryService.createProcessDefinitionQuery().count();
    }
    
    @Transactional
    public List<ProcessModel> listProcess(){
    	List<ProcessModel> lsm=new ArrayList<ProcessModel>();
    	
    	 List<ProcessDefinition> pdn=repositoryService.createProcessDefinitionQuery().
    			 orderByProcessDefinitionVersion().desc().list();
    	 
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
    public void startProcessByKey(String processKey,Map<String,Object> process_variables){
    	
    	
    	
    	runtimeService.startProcessInstanceByKey(processKey,process_variables);
  	    
    	try{
    		
    		 identityService.setAuthenticatedUserId("009697");
    		 
    		 runtimeService.startProcessInstanceByKey(processKey,process_variables);
    		
    	}finally{
    		
    		identityService.setAuthenticatedUserId(null);
    	}
    	
    	
    }
    
    @Transactional
    public void startProcessById(String processId,Map<String,Object> process_variables){
    	
    	runtimeService.startProcessInstanceById(processId,process_variables);
     	
    }
    
    @Transactional
    public  InputStream getProcessDiagram(String processDefinitionId){
    	
    	processDefinitionId="oneTaskProcess:9:57507";
    	
    	ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
    			.processDefinitionId(processDefinitionId).singleResult();
    			//.processDefinitionKey(processDefinitionName).singleResult();
    	
    	String diagramResourceName = processDefinition.getDiagramResourceName();
    	
    	InputStream imageStream = repositoryService.
    			getResourceAsStream(processDefinition.getDeploymentId(), diagramResourceName);
    	
    
    	//repositoryService.getBpmnModel(processDefinitionId);
    	// List<String> activities =
          //       runtimeService.getActiveActivityIds(processDefinitionId);
    
    	// ProcessDiagramGenerator.generateDiagram(repositoryService.getBpmnModel(processDefinitionId),"png",activities);
    	 
    	
    	
    	return imageStream;
    	
    }
    
    @Transactional
    public int countPendingTask(String assignee){
    	
    	return taskService.createTaskQuery().list().size();
    }
    
    @Transactional
    public long countCompletedTask(String assignee){
    	
    	return historyService.createHistoricTaskInstanceQuery().finished().count();
    }
    
    @Transactional
    public List<TaskModel> detailsCompletedTask(String assignee){
    	List<TaskModel> lstm=new ArrayList<TaskModel>();
    	
    	 List<HistoricTaskInstance> lsthc=historyService.
    			 createHistoricTaskInstanceQuery().finished().list();
    	 
    	 
    	 for(HistoricTaskInstance history:lsthc){
    		 TaskModel model=new TaskModel();
    		 model.setId(history.getId());
    		 model.setName(history.getName());
    		 
    		 history.getClaimTime();
    		 history.getCreateTime();
    		 history.getEndTime();
    		 
    		 history.getDescription();
    		 
    		// history.ge
    		 
    		 lstm.add(model);
    		 
    	 }
    	 return lstm;
    }
    
    @Transactional
    public List<TaskModel> getAllTasks() {
    	List<TaskModel> lstm=new ArrayList<TaskModel>();
    	
        List<Task> tasks=taskService.createTaskQuery().list();
        
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
    public List<TaskModel> getTasksForAssignee(String assignee) {
    	List<TaskModel> lstm=new ArrayList<TaskModel>();
    	
        List<Task> tasks=taskService.createTaskQuery().taskAssignee(assignee).list();
        
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
    public void getUserTaskFormData(String taskId){
    	
    	TaskFormData tfd=formService.getTaskFormData(taskId);
    	
    	List<FormProperty> lft=tfd.getFormProperties();
    	
    	for(FormProperty property:lft){
    		
    		property.getName();
    		
    	}
    	
    }
    
    @Transactional
    public void submitUserTaskFormData(String taskId,
    		Map<String,String> form_properties,
    		Map<String,Object> process_variables){
    	
    	//taskService.complete(arg0);
    	
    	//How to get process variable inside task
    	//Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
    	//Map<String,Object> process_var=task.getProcessVariables();
    	
    	
    	formService.submitTaskFormData(taskId,form_properties);
    	
  
    	
    }
    
    @Transactional
    public void getTaskData(String taskId){
    	
    	// Historic Process Instance Query
    	
    	List<HistoricProcessInstance>  lst =historyService.createHistoricProcessInstanceQuery().finished().list();
    	
    	for(HistoricProcessInstance hdl:lst){
    		
    		
    		
    		log.info(hdl.getEndTime().toString() + "--" +hdl.getId() + "--" +hdl.getProcessDefinitionId());
    		
    	}
    	
    	//Historic Process Variable Instance Query
    	//historyService.createHistoricVariableInstanceQuery();
    	
    	
    	//Historic Activity(all type activity) Instance Query
    	//historyService.createHistoricActivityInstanceQuery()
    	    	
    	//Historic Detail Query for task and start process
    	
    	List<HistoricDetail> lhd=historyService.createHistoricDetailQuery().formProperties().taskId("123").list();
    	
    	for(HistoricDetail historicDetail:lhd){
    		
    		if (historicDetail instanceof HistoricFormProperty) {
				HistoricFormProperty form = (HistoricFormProperty) historicDetail;
				System.out.println(String.format("form->, key: %s, value: %s", form.getPropertyId(), form.getPropertyValue()));
			}
    		else if (historicDetail instanceof HistoricVariableUpdate) {
				HistoricVariableUpdate varEntity = (HistoricVariableUpdate) historicDetail;
				System.out.println(String.format("variable->, key: %s, value: %s", varEntity.getVariableName(), varEntity.getValue()));
			}
    	}
    	
    	List<HistoricTaskInstance> lc=historyService.createHistoricTaskInstanceQuery().finished().list();
    	  
    	for(HistoricTaskInstance h:lc){
    		
    		log.info("finished" + h.getId());
    	}
    	
    	
    }

}
