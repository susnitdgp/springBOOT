package com.kaniha.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class CustomErrorController implements ErrorController {

	@Autowired
    private ErrorAttributes errorAttributes;
	
	private boolean debug;
	
	private static final String PATH = "/error";
	
	private static final Logger log = LoggerFactory.getLogger(CustomErrorController.class);
	
	
	@RequestMapping(value = PATH,produces=MediaType.TEXT_HTML_VALUE)
	public String errorFunction(HttpServletRequest request, HttpServletResponse response,Model model) {
	        // Appropriate HTTP response code (e.g. 404 or 500) is automatically set by Spring. 
	        // Here we just define response body.
	    this.debug=true;
		
	    ErrorObject error=new ErrorObject(response.getStatus(), getErrorAttributes(request, debug));
	    
		model.addAttribute("errorObject",error);
	   
		log.info( Integer.toString(response.getStatus()));
		log.info(error.getMessage());
		log.info(error.getTimeStamp());
		
		return "error";
	}
	@Override
	public String getErrorPath() {
		
		return PATH;
	}
			
	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }

}
