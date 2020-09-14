package com.example.Property.Management.utility;


import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.Owner;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.*;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configurable
public class CallAPI {

    private final RestTemplate restTemplate;

    public CallAPI() {
        this.restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
    }


    public String callHome(String token, String principle){
        //init
        // Set the Accept header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application","string")));
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

        String url = "http://localhost:8080/api/home/"+ principle;
        System.out.println("restTemplate: " + restTemplate);
        System.out.println("url: " + url);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );
        String data = responseEntity.getBody();
        System.out.println("data: " + data);

        return data;
    }

}
