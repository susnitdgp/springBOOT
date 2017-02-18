package com.kaniha.commonspare.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="groupcode_matcode_mapping")
@IdClass(GroupMaterialPK.class)
public class GroupMaterial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="group_code")
	private String groupCode;
	
	@Id
	@Column(name="mat_code")
	private String matCode;
	
	@Id
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
	
	@ManyToOne
	@JoinColumn(name="mat_code",insertable=false,updatable=false)
	private MaterialMaster matMast;
	
	
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	

	public String getMatCode() {
		return matCode;
	}

	public void setMatCode(String matCode) {
		this.matCode = matCode;
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

	public MaterialMaster getMatMast() {
		return matMast;
	}

	public void setMatMast(MaterialMaster matMast) {
		this.matMast = matMast;
	}

	
	
}
