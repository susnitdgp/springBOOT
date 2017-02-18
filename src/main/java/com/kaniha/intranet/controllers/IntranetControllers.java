package com.kaniha.intranet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IntranetControllers {
	
	
	
	@RequestMapping(value={"/intranet/home"},method=RequestMethod.GET)
	public String home(){
		
		return "intranet/home";
		
	}

}
