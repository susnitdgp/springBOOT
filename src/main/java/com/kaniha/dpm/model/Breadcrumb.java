package com.kaniha.dpm.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Breadcrumb implements Serializable {
	
	
	private String label;
	
	private String url;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
