package com.example.Property.Management.api;


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
public class initAPI {

    private final RestTemplate restTemplate;

    @Autowired
    public initAPI () {
        this.restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
    }


    public void initAPIHello(String token, String principle){
        //init
        // Set the Accept header
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(Collections.singletonList(new MediaType("application","json")));
        requestHeaders.setBearerAuth(token);
        HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

        String url = "http://localhost:8080/api/home/"+ principle;
        System.out.println("restTemplate: " + restTemplate);
        System.out.println("url: " + url);
        ResponseEntity<UserDto> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                UserDto.class
        );
        UserDto userDto = responseEntity.getBody();
        //System.out.println("userDto: " + userDto);
    }

}
