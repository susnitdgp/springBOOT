package com.kaniha.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="EAPPS_ROLE_MAPPING")
@IdClass(EappsRolePK.class)
public class EappsRoleMap implements Serializable {
	
	@Id
	@Column(name="emp_num")
	private String emp_num;
	
	@Id
	@Column(name="role_alloted")
	private String role_alloted;

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
	
	

}
