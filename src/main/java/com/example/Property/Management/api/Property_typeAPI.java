package com.example.Property.Management.api;

import com.example.Property.Management.entity.Property_type;
import com.example.Property.Management.repository.Property_typeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="crud",produces="application/json")
public class Property_typeAPI {

    @Autowired
    private Property_typeRepository propertyTypeRepository;


    @GetMapping("/allProperty_types")
    public List<Property_type> getAll() {

        System.out.println("getAll properties \n");

        return propertyTypeRepository.findAll();
    }

}
