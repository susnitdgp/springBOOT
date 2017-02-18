package com.kaniha.activity.model;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class FetchDataFromPI implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("DATA From PI Fetched: " +new Date());
		execution.setVariable("smsToBeSend", true);
		
	}

}
