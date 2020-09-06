package com.example.Property.Management.api;

import com.example.Property.Management.entity.Property;
import com.example.Property.Management.entity.Transaction_type;
import com.example.Property.Management.repository.PropertyRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(path = "api/property/", produces = "application/json")
public class PropertyAPI {

    private PropertyRepository propertyRepository;

    @GetMapping("{id}")
    public Property getProperty(@PathParam("id") Long id) {
        Property property = propertyRepository.getOne(id);
        return property;
    }

    @PostMapping
    public void saveProperty(@Validated @RequestBody Property property){
        Property property1 = propertyRepository.save(property);
    }

}
