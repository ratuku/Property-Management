package com.example.Property.Management.api;

import com.example.Property.Management.entity.Lease_agreement;
import com.example.Property.Management.repository.Lease_agreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/lease", produces = "application/json")
public class Lease_agreementAPI {

    @Autowired
    private Lease_agreementRepository leaseAgreementRepository;

    @RequestMapping("/{id}")
    public Lease_agreement getLease_agreement(@PathVariable("id") Long id){
        Optional<Lease_agreement> lease_agreement = leaseAgreementRepository.findById(id);
        return lease_agreement.get();
    }

    @PostMapping
    public Lease_agreement saveLeaseAgreement(@Validated @RequestBody Lease_agreement lease_agreement){
        Lease_agreement lease_agreement1 = leaseAgreementRepository.save(lease_agreement);
        return lease_agreement1;
    }

}
