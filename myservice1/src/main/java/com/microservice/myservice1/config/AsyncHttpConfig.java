package com.microservice.myservice1.config;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AsyncHttpConfig {
	@Bean
	public AsyncHttpClient asyncHttpClient(){
		AsyncHttpClientConfig.Builder builder=new AsyncHttpClientConfig.Builder().
				setRequestTimeout(5000).setConnectTimeout(2000).setReadTimeout(3000);
		return new AsyncHttpClient(builder.build());
	}
}
