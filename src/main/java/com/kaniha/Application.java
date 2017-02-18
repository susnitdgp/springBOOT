package com.kaniha;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	/*
	@Bean
    public CompressHtmlFilter compressHtmlFilter() {
        return new CompressHtmlFilter();
    }
	
	@Bean
    public WhitespaceFilter whitespaceFilter() {
        return new WhitespaceFilter();
    }*/
	
	@Bean
	public RestTemplate restTemplate() {
	    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

	    Proxy proxy= new Proxy(Type.HTTP, new InetSocketAddress("10.0.9.54", 8080));
	    requestFactory.setProxy(proxy);

	    return new RestTemplate(requestFactory);
	}
	
}
