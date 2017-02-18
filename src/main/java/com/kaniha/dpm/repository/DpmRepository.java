package com.kaniha.dpm.repository;


import java.util.List;

import com.kaniha.dpm.model.Content;



public interface DpmRepository {
	
	public List<Content> getAllFiles(String directoryName);
	
	public void recursiveParent(String directoryName);
	
}
