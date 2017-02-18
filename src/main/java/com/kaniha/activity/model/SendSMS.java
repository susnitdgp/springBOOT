package com.kaniha.activity.model;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class SendSMS implements JavaDelegate {

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		
		System.out.println("SMS SEND: "+ new Date());
		
	}

}
