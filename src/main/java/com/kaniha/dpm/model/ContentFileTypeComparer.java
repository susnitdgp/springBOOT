package com.kaniha.dpm.model;

import java.util.Comparator;

public class ContentFileTypeComparer implements Comparator<Content> {

	@Override
	public int compare(Content o1, Content o2) {
		
		if(o1.isFolder()){
			return -11;
		}
		if(o2.isFolder()){
			return 1;
		}
		else{
			return 0;
		}
	}

}
