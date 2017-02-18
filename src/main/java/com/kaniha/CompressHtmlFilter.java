package com.kaniha;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

public class CompressHtmlFilter implements Filter {

	
	private HtmlCompressor compressor;
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		 CharResponseWrapper capturingResponseWrapper = new CharResponseWrapper((HttpServletResponse) response);

	     filterChain.doFilter(request, capturingResponseWrapper);
	     
	     String content = capturingResponseWrapper.getCaptureAsString();
	     
	     if(response.getContentType() != null  && response.getContentType().startsWith("text/html")) {
	       
	        	
	           //Replace stuff here
	           String replacedContent = compressor.compress(content);
	                   
	           //System.out.println(replacedContent);
	            
	            response.setContentLength(replacedContent.getBytes().length);
	            
	            PrintWriter out = response.getWriter();
	            out.write(replacedContent);
	            out.close();
	            
	            //response.getWriter().write(replacedContent);
	      }
	
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		compressor = new HtmlCompressor();
	
	    //compressor.setCompressCss(true);
		//compressor.setCompressJavaScript(true);
		
	}
	
	@Override
	public void destroy() {
		
	}


}
