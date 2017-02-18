package com.kaniha.auth.service;

import java.util.List;

import com.kaniha.auth.entity.EmpMast;

public interface LoginService {
	
	//public boolean checkEmployeeExist(String emp_num);
	
	
	public boolean checkLogin(String username,String password);
	public List<String> getEAPPSRoles(String emp_num);
	public EmpMast getUserDetails(String emp_num);
	
	
	/*
	public boolean setLoginAttemptCount(String emp_num);
	public Integer getLoginAttemptCount(String emp_num);
	
	
	public String getMobileno(String emp_num);
	public String randomPassGen();
	public String setTempPass(String emp_num);
	
	
	public boolean isTempActive(String emp_num);
	public boolean isTempLoginAllowed(String emp_num,String temp_pass);
	
	public void setPass(String emp_num,String password);
	
	public Integer durationAtKaniha(String emp_num);
	*/

}
