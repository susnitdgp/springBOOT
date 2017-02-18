package com.kaniha.auth.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="EA_webuser_tstpp")
public class WebUser implements Serializable {
	@Id
	@Column(name="emp_num")
	private String emp_num;
	
	@Column(name="passwd")
	private String password;
	
	@Column(name="temp_password")
	private String temp_password;
	
	@Column(name="temp_status")
	private Integer temp_status;
	
	@Column(name="login_attempt_count")
	private Integer login_count;
	
	@OneToOne(mappedBy="wu", cascade=CascadeType.ALL)
	private EmpMast emp;

	
	
	public String getEmp_num() {
		return emp_num;
	}

	public void setEmp_num(String emp_num) {
		this.emp_num = emp_num;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public EmpMast getEmp() {
		return emp;
	}

	public void setEmp(EmpMast emp) {
		this.emp = emp;
	}

	public Integer getLogin_count() {
		return login_count;
	}

	public void setLogin_count(Integer login_count) {
		this.login_count = login_count;
	}

	public String getTemp_password() {
		return temp_password;
	}

	public void setTemp_password(String temp_password) {
		this.temp_password = temp_password;
	}

	public Integer getTemp_status() {
		return temp_status;
	}

	public void setTemp_status(Integer temp_status) {
		this.temp_status = temp_status;
	}
	
	

}
