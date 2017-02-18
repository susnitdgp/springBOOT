package com.kaniha.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EA_DeptCode_Mas")
@SuppressWarnings("serial")
public class DeptMast implements Serializable {
	
	@Id
	@Column(name="Dept_id")
	private String dept_code;
	
	
	@Column(name="deptName")
	private String dept_name;


	public String getDept_code() {
		return dept_code;
	}


	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}


	public String getDept_name() {
		return dept_name;
	}


	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	
	

}
