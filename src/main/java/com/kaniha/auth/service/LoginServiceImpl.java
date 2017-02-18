package com.kaniha.auth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kaniha.auth.repository.EappsRoleMapRepository;
import com.kaniha.auth.repository.EmpMastRepository;
import com.kaniha.auth.repository.WebUserRepository;

import com.kaniha.auth.entity.EmpMast;
import com.kaniha.auth.entity.WebUser;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private WebUserRepository webUserRepository;
	
	@Autowired
	EmpMastRepository empMastRepository;
	
	@Autowired
	EappsRoleMapRepository eappsRoleMapRepository;
	
	@Override
	public boolean checkLogin(String username, String password) {
				
		 Optional<WebUser> users=webUserRepository.checkLogin(username, password);
		 
		 if(users.isPresent()){
			 
			 WebUser wu=users.get();
			 
			 if(wu!=null){
				 
				 	if (wu.getEmp().getPay_status().equals("A") ){
						return true;
					}else{
						
						return false;
					}
		
			 }else{
				 
				 return false;
			 }
			 
			 
		 }else{
			 return false;
		 }

		
	}
	
	@Override
	public List<String> getEAPPSRoles(String emp_num) {
		
		List<String> ls=new ArrayList<String>();
		
		List<String> roles=eappsRoleMapRepository.getEAPPSRoles(emp_num);
		
		
		if (roles.size() == 0){
			
			ls.add("EAPPS_USER");
		
		}else{
			
			ls.add("EAPPS_USER");
			
			for(String s:roles){
				
				ls.add(s);
			}
			
		}
		
		return ls;
		
		
	}
	@Override
	public EmpMast getUserDetails(String emp_num) {
		
		return empMastRepository.getUserDetails(emp_num);
	}

}
