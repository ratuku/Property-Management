package com.example.Property.Management.api;

import com.example.Property.Management.entity.Owner;
import com.example.Property.Management.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/owner", produces = "application/json")
public class OwnerAPI {

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwner(@PathVariable("id") Long id) {
        System.out.println("Id: " + id);
        Optional<Owner> owner = ownerRepository.findById(id);
        return new ResponseEntity<>(owner.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Owner> saveOwner(@Validated @RequestBody Owner owner){
        Owner owner1 = ownerRepository.save(owner);
        return  new ResponseEntity<>(owner1, HttpStatus.OK);
    }

}
