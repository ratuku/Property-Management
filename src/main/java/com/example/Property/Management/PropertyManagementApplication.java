package com.example.Property.Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PropertyManagementApplication {

/*	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		RestTemplate restTemplate = builder.build();
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
		return restTemplate;
	}*/

	public static void main(String[] args) {
		SpringApplication.run(PropertyManagementApplication.class, args);
	}

}
