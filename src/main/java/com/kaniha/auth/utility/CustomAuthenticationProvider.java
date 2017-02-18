package com.kaniha.auth.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.kaniha.auth.entity.EmpMast;
import com.kaniha.auth.service.LoginService;




@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {
 
   
	@Autowired
	private LoginService loginService;
	
	@Override
    public Authentication authenticate(Authentication authentication)
    		throws AuthenticationException,
    		BadCredentialsException {
    	
		String emp_num = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        if (loginService.checkLogin(emp_num, password)) {
        	
        	try{
        		
        		List<String> ls=loginService.getEAPPSRoles(emp_num);
                
                List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
                
                Iterator<String> it=ls.iterator();
                
                while(it.hasNext()){
                	
                	String s=(String)it.next();
                	grantedAuths.add(new SimpleGrantedAuthority(s));
                }
                
                EmpMast emp=loginService.getUserDetails(emp_num);
               
                UserDetails userdetails=new UserInfo(emp_num,emp.getEmp_name().trim(),emp.getEmp_sex().trim(),grantedAuths,emp.getDesignation_code(),emp.getDm().getDept_name(),emp.getDm().getDept_code());
                
               //Test User
               //UserDetails s =new User("SUSANTA GOSWAMI",password,true,true,true,true,grantedAuths);
                
                Authentication auth = new UsernamePasswordAuthenticationToken(userdetails, password, grantedAuths);
               
                return auth;
        	}
        	catch(Exception e){
        		
        		throw new AuthenticationCredentialsNotFoundException("Username or password was not accepted", e);
        	}
        	
        	
            
        } else {
        	
            return null;
        }
    }
 
    @Override
    public boolean supports(Class<?> arg0) {
    	return arg0.equals(UsernamePasswordAuthenticationToken.class);
        
    }
}
