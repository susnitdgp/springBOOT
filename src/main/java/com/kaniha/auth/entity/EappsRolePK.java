package com.kaniha.auth.entity;

import java.io.Serializable;

public class EappsRolePK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String emp_num;
	
	private String role_alloted;
	
	
	public EappsRolePK(){}
	
	
	public EappsRolePK(String emp_num,String role_alloted){
		this.emp_num=emp_num;
		this.role_alloted=role_alloted;
	}


	public String getEmp_num() {
		return emp_num;
	}


	public void setEmp_num(String emp_num) {
		this.emp_num = emp_num;
	}


	public String getRole_alloted() {
		return role_alloted;
	}


	public void setRole_alloted(String role_alloted) {
		this.role_alloted = role_alloted;
	}


	@Override
	public int hashCode() {
		
		int hshCode;	
		hshCode= emp_num.hashCode() + role_alloted.hashCode();	
		return hshCode;
	
	}


	@Override
	public boolean equals(Object obj) {
		
		if(obj==null){
			return false;
		}
		if(!(obj instanceof EappsRolePK)){
			return false;
		}
		
		EappsRolePK obj1=(EappsRolePK)obj;
		
		if(this.emp_num.equals(obj1.emp_num) && this.role_alloted.equals(obj1.role_alloted)){
			return true;
		}else{
			return false;
		}
	}
	


}
