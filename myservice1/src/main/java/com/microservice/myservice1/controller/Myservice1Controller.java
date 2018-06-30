package com.microservice.myservice1.controller;

import java.net.URI;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.HttpClientRequest;
import com.github.kristofa.brave.http.HttpClientRequestAdapter;
import com.github.kristofa.brave.http.HttpClientResponseAdapter;
import com.github.kristofa.brave.http.HttpResponse;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;

import io.swagger.annotations.Api;

@Api("myservice1 相关api")
@RestController
@RequestMapping("/myservice1/zipkin")
public class Myservice1Controller {
	@Autowired
	private AsyncHttpClient asyncHttpClient;
	@Autowired
	private Brave brave;
	
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public String test(){
		try{
			/**************service2*************/
			RequestBuilder builder2=new RequestBuilder();
			String url2="http://localhost:8082/myservice2/zipkin/test";
			builder2.setUrl(url2);			
			Request request2=builder2.build();
			
			clientRequestInterceptor(request2);
			Future<Response> response2=asyncHttpClient.executeRequest(request2);
			clientResponseInterceptor(response2.get());		
			
			return response2.get().getResponseBody();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	private void clientRequestInterceptor(Request request) {
		brave.clientRequestInterceptor().handle(new HttpClientRequestAdapter(new HttpClientRequest(){
			@Override
			public URI getUri(){
				return URI.create(request.getUrl());
			}
			
			@Override
			public String getHttpMethod(){
				return request.getMethod();
			}
			
			@Override
			public void addHeader(String headerKey,String headerValue){
				request.getHeaders().add(headerKey, headerValue);
			}
		},new DefaultSpanNameProvider()));
	}

	private void clientResponseInterceptor(Response response) {
		brave.clientResponseInterceptor().handle(new HttpClientResponseAdapter(new HttpResponse(){
			public int getHttpStatusCode(){
				return response.getStatusCode();
			}
		}));
	}


}
