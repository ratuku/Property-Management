package com.example.Property.Management.api;

import com.example.Property.Management.entity.Bank;
import com.example.Property.Management.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping(path = "api/bank", produces = "application/json")
public class BankAPI {

    @Autowired
    private BankRepository bankRepository;

    @RequestMapping("/{id}")
    public Bank getBankEntry(@PathParam("id") long id){
        Bank bank = bankRepository.getOne(id);
        return bank;
    }

    @PostMapping
    public Bank saveBankEntry(@Validated @RequestBody Bank bank){
        Bank bank1 = bankRepository.save(bank);
        return bank1;
    }

    //https://stackoverflow.com/questions/52321463/how-to-call-a-rest-controller-from-another-rest-controller
    //https://docs.spring.io/autorepo/docs/spring-android/1.0.x/reference/html/rest-template.html
}
