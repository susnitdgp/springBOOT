package com.kaniha.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.kaniha.auth.utility.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.authorizeRequests()
         .antMatchers("/css/**").permitAll();
		 http.authorizeRequests()
         .antMatchers("/img/**").permitAll();
		 http.authorizeRequests()
         .antMatchers("/js/**").permitAll();
		 http.authorizeRequests()
         .antMatchers("/pics/**").permitAll();
		 http.authorizeRequests()
         .antMatchers("/codebase/**").permitAll();
		
		 http.authorizeRequests()
         .antMatchers("/dpm/**").permitAll();
				
		 http.authorizeRequests().anyRequest().authenticated();
		 		
		//http.authorizeRequests().anyRequest().permitAll();
		 
		 		 
		 http
		 .formLogin().failureUrl("/login?error")
         .defaultSuccessUrl("/home")
         .loginPage("/login")
         .permitAll()
         .and()
         .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
         .permitAll(); 
		 
		
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //auth
        //    .inMemoryAuthentication()
         //       .withUser("user").password("password").roles("GOSWAMI");
        
       auth.authenticationProvider(this.customAuthenticationProvider);
        
    }
	
	
	

}