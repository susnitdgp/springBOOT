package com.kaniha.activity.model;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class RiskChecker implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String name = (String) execution.getVariable("name");
		
		
		
		System.out.println("Checking loan risk for : " + name);
		boolean riskCheckOk = false;
		if(!name.equalsIgnoreCase("Evil Harry")) { 			
			riskCheckOk = true;
		}
		execution.setVariable("riskCheckOk", riskCheckOk);
		
	}

}
