package com.kaniha.commonspare.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;


public class GroupDepartment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name="group_code")
	private String groupCode;
	
	@Column(name="dept_code")
	private String deptCode;
	
	@Column(name="for_year")
	private String forYear;
	
	@Column(name="add_by")
	private String addBy;
	
	@Column(name="add_time_stamp")
	private Date addTimeStamp;
	
	@Column(name="status")
	private String status;
	
	@Column(name="delete_flag")
	private String deleteFlag;
	
	
	
	
	

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getForYear() {
		return forYear;
	}

	public void setForYear(String forYear) {
		this.forYear = forYear;
	}

	public String getAddBy() {
		return addBy;
	}

	public void setAddBy(String addBy) {
		this.addBy = addBy;
	}

	public Date getAddTimeStamp() {
		return addTimeStamp;
	}

	public void setAddTimeStamp(Date addTimeStamp) {
		this.addTimeStamp = addTimeStamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	

	
}
