package com.kaniha.auth.utility;

import java.util.Collection;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



@SuppressWarnings("serial")
public class UserInfo implements UserDetails {
	
	private String emp_num;
	private String name;
	private String dept_name;
	private String dept_code;
	private String designation;
	private String logintime;
	private String sex;
	private List<GrantedAuthority> ga;
	
	
	@Override
	public int hashCode() {
		
		int hash=3;
		hash=7* hash* this.emp_num.hashCode();
		return hash;
	
	}



	@Override
	public boolean equals(Object obj) {
		boolean result;
		
		if(obj == null || obj.getClass() != getClass() ){
			result=false;
		}
		else{
			
			UserInfo u=(UserInfo)obj;
			if( this.emp_num.equals(u.getEmp_num())){
				result=true;
			}else{
				
				result=false;
			}
			
		}
		return result;
	}
	
	
	
	
	public UserInfo(String num,String emp_name,String sex_code, List<GrantedAuthority> auth,String desig_code, String dep_name,String dep_code){
		this.emp_num=num;
		this.name=StringUtils.capitalize(emp_name);
		this.sex=StringUtils.capitalize(sex_code);
		this.dept_name=StringUtils.capitalize(dep_name);
		this.dept_code=dep_code;
		this.designation=desig_code;
		this.logintime=new Date().toString();
		
		this.ga=auth;
		
	}
	
	
	
	public String getEmp_num() {
		return emp_num;
	}

	public void setEmp_num(String emp_num) {
		this.emp_num = emp_num;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLogintime() {
		return logintime;
	}
	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDept_name() {
		return dept_name;
	}



	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}



	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.ga;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.name;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	

}
