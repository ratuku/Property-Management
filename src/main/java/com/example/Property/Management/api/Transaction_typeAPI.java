package com.example.Property.Management.api;

import com.example.Property.Management.entity.Transaction_type;
import com.example.Property.Management.repository.Transaction_typeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/transaction_type",produces="application/json")
public class Transaction_typeAPI {

    @Autowired
    private Transaction_typeRepository typeRepository;

    @GetMapping()
    public List<Transaction_type> getTransaction_types(){
        List<Transaction_type> types = typeRepository.findAll();
        return types;
    }

}
