package com.kaniha.dpm.service;

import java.util.List;

import com.kaniha.dpm.model.Breadcrumb;
import com.kaniha.dpm.model.Content;

public interface DpmService {
	
	public List<Content> getAllFiles(String directoryName);
	
	public  void recursiveParent(String directoryName,List<Breadcrumb> lsb);

}
