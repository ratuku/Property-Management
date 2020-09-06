package com.example.Property.Management.api;

import com.example.Property.Management.entity.Lease_agreement;
import com.example.Property.Management.repository.Lease_agreementRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping(path = "/api/lease/", produces = "application/json")
public class Lease_agreementAPI {

    private Lease_agreementRepository leaseAgreementRepository;

    @RequestMapping("{id}")
    public Lease_agreement getLease_agreement(@PathParam("id") long id){
        Lease_agreement lease_agreement = leaseAgreementRepository.getOne(id);
        return lease_agreement;
    }

    @PostMapping
    public Lease_agreement saveLeaseAgreement(@Validated @RequestBody Lease_agreement lease_agreement){
        Lease_agreement lease_agreement1 = leaseAgreementRepository.save(lease_agreement);
        return lease_agreement1;
    }

}
