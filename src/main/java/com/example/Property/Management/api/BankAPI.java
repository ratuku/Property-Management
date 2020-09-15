package com.example.Property.Management.api;

import com.example.Property.Management.entity.Bank;
import com.example.Property.Management.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/bank", produces = "application/json")
public class BankAPI {

    @Autowired
    private BankRepository bankRepository;

    @GetMapping("/{id}")
    public List<Bank> getBankEntry(@PathVariable("id") Long id){
        List<Bank> bankEntries = bankRepository.findAllByOwner_OwnerId(id);

        return bankEntries;
    }

    @PostMapping
    public Bank saveBankEntry(@Validated @RequestBody Bank bank){
        Bank bank1 = bankRepository.save(bank);
        return bank1;
    }

}
