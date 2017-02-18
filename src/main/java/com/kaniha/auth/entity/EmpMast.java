package com.kaniha.auth.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Table(name="ts_emp_mast")
@Entity
public class EmpMast implements Serializable {
	
	@Id
	@Column(name="emp_num")
	private String emp_num;
	
	@Column(name="emp_name")
	private String emp_name;
	
	@Column(name="emp_sex_code")
	private String emp_sex;
	
	@Column(name="pay_status")
	private String pay_status;
	
	@Column(name="designation_code")
	private String designation_code;
	
	@Column(name="num_mob")
	private String mobile_number;
	
	@Column(name="cadre")
	private String cadre;
	
	@Column(name="fstpp_joining_date")
	private Date kaniha_join_date;
	
	
	@ManyToOne
	@JoinColumn(name="dept_code")
	private DeptMast dm;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private WebUser wu;

	public WebUser getWu() {
		return wu;
	}

	public void setWu(WebUser wu) {
		this.wu = wu;
	}

	public DeptMast getDm() {
		return dm;
	}

	public void setDm(DeptMast dm) {
		this.dm = dm;
	}

	public String getEmp_sex() {
		return emp_sex;
	}

	public void setEmp_sex(String emp_sex) {
		this.emp_sex = emp_sex;
	}

	public String getEmp_num() {
		return emp_num;
	}

	public void setEmp_num(String emp_num) {
		this.emp_num = emp_num;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getPay_status() {
		return pay_status;
	}

	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getDesignation_code() {
		return designation_code;
	}

	public void setDesignation_code(String designation_code) {
		this.designation_code = designation_code;
	}

	public String getCadre() {
		return cadre;
	}

	public void setCadre(String cadre) {
		this.cadre = cadre;
	}

	public Date getKaniha_join_date() {
		return kaniha_join_date;
	}

	public void setKaniha_join_date(Date kaniha_join_date) {
		this.kaniha_join_date = kaniha_join_date;
	}

	
}
