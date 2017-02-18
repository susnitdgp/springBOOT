package com.kaniha.auth.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


public class AuthenticationWrapper implements Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Authentication original;
	private List<GrantedAuthority> extraRoles;

	public AuthenticationWrapper( Authentication original, List<GrantedAuthority> extraRoles ){
	      this.original = original;
	      this.extraRoles = extraRoles;
	}
	
	
	@Override
	public String getName() {
		
		return original.getName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List <GrantedAuthority> originalRoles = (List<GrantedAuthority>) original.getAuthorities();
		
		Set<GrantedAuthority> newRoles= new HashSet<GrantedAuthority>();
		
		List<GrantedAuthority> ret=new ArrayList<GrantedAuthority>();
		
		for (GrantedAuthority g:originalRoles){
		
			newRoles.add(g);
			
		}
		
		for(GrantedAuthority g1:this.extraRoles){
			newRoles.add(g1);
		}
		
		for(GrantedAuthority g2:newRoles){
			
			ret.add(g2);
		}
				
		return ret;
		
	}

	@Override
	public Object getCredentials() {
		
		return original.getCredentials();
	}

	@Override
	public Object getDetails() {
		
		return original.getDetails();
	}

	@Override
	public Object getPrincipal() {
		
		return original.getPrincipal();
	}

	@Override
	public boolean isAuthenticated() {
		
		return original.isAuthenticated();
	}

	@Override
	public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
		original.setAuthenticated( arg0 );
		
	}

	
}
