package com.kaniha.activity.config;

import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;

public class Test extends StandaloneInMemProcessEngineConfiguration {

	@Override
	public ProcessEngineConfiguration setHistory(String history) {
		
		return super.setHistory(history);
	}
	

}
