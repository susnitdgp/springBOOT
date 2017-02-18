package com.kaniha.controllers;

import java.io.Serializable;
import org.apache.commons.math3.util.Precision;

public class AaqmsData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String CO;
	
	private String CO2;
	
	private String NOX;
	
	private String O3;
	
	private String PM10;
	
	private String PM25;
	
	private String SOX;
	
	private String lastUpdate;
	
	public AaqmsData(){
		
		this.lastUpdate="N/A";
		
	}

	public String getCO() {
		return CO;
	}

	//Done
	public void setCO(String cO) {
		Double temp=null;
		try{
			
			temp=Double.parseDouble(cO);
			
			CO=Double.toString(Precision.round(temp,2));
		}
		catch(NumberFormatException ex){
			CO="N/A";
		}
		
	}

	public String getCO2() {
		return CO2;
	}

	//Done
	public void setCO2(String cO2) {
		
		Double temp=null;
		try{
			
			temp=Double.parseDouble(cO2);
			
			CO2=Double.toString(Precision.round(temp,2));
		}
		catch(NumberFormatException ex){
			CO2="N/A";
		}
		
	}

	public String getNOX() {
		return NOX;
	}

	//Done
	public void setNOX(String nOX) {
		
		Double temp=null;
		try{
			
			temp=Double.parseDouble(nOX);
			
			NOX=Double.toString(Precision.round(temp,2));
		}
		catch(NumberFormatException ex){
			NOX="N/A";
		}
	}

	public String getO3() {
		return O3;
	}

	//Done
	public void setO3(String o3) {
		
		Double temp=null;
		try{
			
			temp=Double.parseDouble(o3);
			
			O3=Double.toString(Precision.round(temp,2));
		}
		catch(NumberFormatException ex){
			O3="N/A";
		}
	}

	public String getPM10() {
		return PM10;
	}

	//Done
	public void setPM10(String pM10) {
		PM10 = pM10;
		
		Double temp=null;
		try{
			
			temp=Double.parseDouble(pM10);
			
			PM10=Double.toString(Precision.round(temp,2));
		}
		catch(NumberFormatException ex){
			PM10="N/A";
		}
	}

	public String getPM25() {
		return PM25;
	}

	//Done
	public void setPM25(String pM25) {
		PM25 = pM25;
		
		Double temp=null;
		try{
			
			temp=Double.parseDouble(pM25);
			
			PM25=Double.toString(Precision.round(temp,2));
		}
		catch(NumberFormatException ex){
			PM25="N/A";
		}
	}

	public String getSOX() {
		return SOX;
	}

	//Done
	public void setSOX(String sOX) {
		Double temp=null;
		try{
			
			temp=Double.parseDouble(sOX);
			
			SOX=Double.toString(Precision.round(temp,2));
		}
		catch(NumberFormatException ex){
			SOX="N/A";
		}
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	

}
