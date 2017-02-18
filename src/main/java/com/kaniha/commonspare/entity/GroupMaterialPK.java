package com.kaniha.commonspare.entity;

import java.io.Serializable;


public class GroupMaterialPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String groupCode;
	
	
	private String matCode;
	
	
	private String forYear;
	
	public GroupMaterialPK(){}
	
	public GroupMaterialPK(String groupCode,String matCode,String forYear){
		
		this.groupCode=groupCode;
		this.matCode=matCode;
		this.forYear=forYear;
	}

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

	@Override
	public int hashCode() {
		int hshCode;
		
		hshCode= groupCode.hashCode() + matCode.hashCode() + forYear.hashCode();
		
		return hshCode;
		
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj==null){
			return false;
		}
		
		if(!(obj instanceof GroupMaterialPK )){
			return false;
		}
		
		GroupMaterialPK gm=(GroupMaterialPK)obj;
		
		if(this.forYear.equals(gm.forYear) && this.groupCode.equals(gm.groupCode) && this.matCode.equals(gm.matCode)){
			return true;
		}
		else{
			
			return false;
		}
		
	}
	
	
	
	

}
