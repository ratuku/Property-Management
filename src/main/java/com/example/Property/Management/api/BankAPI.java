package com.example.Property.Management.api;

import com.example.Property.Management.entity.Bank;
import com.example.Property.Management.entity.Lease_agreement;
import com.example.Property.Management.repository.BankRepository;
import com.example.Property.Management.repository.Lease_agreementRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping(path = "/api/bank/", produces = "application/json")
public class BankAPI {

    private BankRepository bankRepository;

    @RequestMapping("{id}")
    public Bank getLease_agreement(@PathParam("id") long id){
        Bank bank = bankRepository.getOne(id);
        return bank;
    }

    @PostMapping
    public Bank saveBankEntry(@Validated @RequestBody Bank bank){
        Bank bank1 = bankRepository.save(bank);
        return bank1;
    }
}
