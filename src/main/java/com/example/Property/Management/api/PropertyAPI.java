package com.example.Property.Management.api;

import com.example.Property.Management.entity.Property_type;
import com.example.Property.Management.service.Property_typeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/crud",produces="application/json")
public class PropertyAPI {

    @Autowired
    private Property_typeRepository propertyTypeRepository;


    @GetMapping("/allProperty_types")
    public List<Property_type> getAll() {
        return propertyTypeRepository.findAll();
    }

    @PostMapping("/property_type")
    public Property_type createProperty_type(@Validated @RequestBody Property_type property_type){

        return propertyTypeRepository.save(property_type);
    }


}
