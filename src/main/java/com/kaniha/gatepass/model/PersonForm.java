package com.kaniha.gatepass.model;

import java.io.Serializable;


import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public class PersonForm implements Serializable {

    
    private String name;

   
    private Integer age;
    
   
    private MultipartFile file;
    
    public PersonForm(){
    	
    }
  
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    
    public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String toString() {
        return "Person(Name: " + this.name + ", Age: " + this.age + ")";
    }
	
	
}
