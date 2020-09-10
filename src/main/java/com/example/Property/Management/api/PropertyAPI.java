package com.example.Property.Management.api;

import com.example.Property.Management.entity.Property;
import com.example.Property.Management.entity.Transaction_type;
import com.example.Property.Management.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/property", produces = "application/json")
public class PropertyAPI {

    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping("/{id}")
    public Property getProperty(@PathParam("id") Long id) {
        Optional<Property> property = propertyRepository.findById(id);
        return property.get();
    }

    @PostMapping
    public void saveProperty(@Validated @RequestBody Property property){
        Property property1 = propertyRepository.save(property);
    }

}
