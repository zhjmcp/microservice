package com.microservice.myservice1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.EmptySpanCollectorMetricsHandler;
import com.github.kristofa.brave.Sampler;
import com.github.kristofa.brave.SpanCollector;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.HttpSpanCollector;
import com.github.kristofa.brave.mysql.MySQLStatementInterceptorManagementBean;
import com.github.kristofa.brave.servlet.BraveServletFilter;

@Configuration
public class ZipkinConfig {
	@Bean
	public SpanCollector spanCollector(){
		HttpSpanCollector.Config spanConfig=HttpSpanCollector.Config.builder()
				.compressionEnabled(false)
				.connectTimeout(5000)
				.readTimeout(6000)
				.flushInterval(1)
				.build();
		
		return HttpSpanCollector.create("http://192.168.118.133:9411", spanConfig,new EmptySpanCollectorMetricsHandler());
	}
	
	@Bean
	public Brave brave(SpanCollector spanCollector){
		Brave.Builder builder=new Brave.Builder("myservice1");
		builder.spanCollector(spanCollector);
		builder.traceSampler(Sampler.create(1));
		return builder.build();
	}
	
	@Bean
	public BraveServletFilter braveServletFilter(Brave brave){
		return new BraveServletFilter(brave.serverRequestInterceptor(),
				brave.serverResponseInterceptor(),
				new DefaultSpanNameProvider());
	}
	
	@Bean
	public MySQLStatementInterceptorManagementBean mySQLStatementInterceptorManagementBean(Brave brave){
		return new MySQLStatementInterceptorManagementBean(brave.clientTracer());
	}
}
