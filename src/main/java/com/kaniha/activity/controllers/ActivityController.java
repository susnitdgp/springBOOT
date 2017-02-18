package com.kaniha.activity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ActivityController {
	
	@RequestMapping(value={"/mats/activity/processes"},method=RequestMethod.GET)
	public String showWorkflow(Model model){
		
				
		return "mats/activity/processes";
	}

}
