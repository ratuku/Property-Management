package com.example.Property.Management.api;

import com.example.Property.Management.entity.Owner;
import com.example.Property.Management.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/owner", produces = "application/json")
public class OwnerAPI {

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/{id}")
    public Owner getOwner(@PathVariable("id") Long id){
        System.out.println("Id: " + id);
        Optional<Owner> owner = ownerRepository.findById(id);
        return  owner.get();
    }

    @PostMapping
    public Owner saveOwner(@Validated @RequestBody Owner owner){
        Owner owner1 = ownerRepository.save(owner);
        return  owner1;
    }

}
