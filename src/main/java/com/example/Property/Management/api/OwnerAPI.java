package com.example.Property.Management.api;

import com.example.Property.Management.entity.Owner;
import com.example.Property.Management.repository.OwnerRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping(path = "api/owner/", produces = "application/json")
public class OwnerAPI {

    private OwnerRepository ownerRepository;

    @GetMapping("{id}")
    public Owner getOwner(@PathParam("id") long id){
        Owner owner = ownerRepository.getOne(id);
        return  owner;
    }

    @PostMapping
    public Owner saveOwner(@Validated @RequestBody Owner owner){
        Owner owner1 = ownerRepository.save(owner);
        return  owner1;
    }

}
