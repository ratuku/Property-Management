package com.example.Property.Management.api;

import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.*;
import com.example.Property.Management.service.DataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Log4j2
@RestController
@RequestMapping(path = "api/home", produces = "application/json")
public class homeAPI {

    private final DataService dataService;

    @Autowired
    public homeAPI(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("{username}")
    public String getUserInfos(@PathVariable("username") String username){

        String returnInfo = dataService.getUserFullInfo(username);

        return returnInfo;
    }

}
