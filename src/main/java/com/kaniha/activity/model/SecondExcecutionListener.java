package com.kaniha.activity.model;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;


@Component
public class SecondExcecutionListener implements ExecutionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateExecution arg0) throws Exception {
		
		arg0.setVariable("test", "test_value");
	}

}
