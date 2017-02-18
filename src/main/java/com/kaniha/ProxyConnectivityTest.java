package com.kaniha;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ProxyConnectivityTest {
	
	public String testProxies(){
		
		Proxy localProxy= new Proxy(Type.HTTP, new InetSocketAddress("10.0.120.214", 8123));
		Proxy corpProxy= new Proxy(Type.HTTP, new InetSocketAddress("10.0.9.54", 8080));
		
		
				
		List<Proxy> lsp=new ArrayList<Proxy>();
		lsp.add(localProxy);
		lsp.add(corpProxy);
		
		return "N/A";
		 
	}

}
