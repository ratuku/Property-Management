package com.example.Property.Management.api;

import com.example.Property.Management.auth.UserService;
import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.*;
import com.example.Property.Management.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Log4j2
@RestController
@RequestMapping(path = "api/home")
public class homeAPI {

    private final UserService userService;
    private final OwnerRepository ownerRepository;
    private final PropertyRepository propertyRepository;
    private final Lease_agreementRepository leaseAgreementRepository;
    private final BankRepository bankRepository;
    private final Transaction_typeRepository transactionTypeRepository;
    private final Property_typeRepository propertyTypeRepository;

    @Autowired
    public homeAPI(UserService userService, OwnerRepository ownerRepository, PropertyRepository propertyRepository,
                   Lease_agreementRepository leaseAgreementRepository, BankRepository bankRepository, Transaction_typeRepository transactionTypeRepository, Property_typeRepository propertyTypeRepository) {
        this.userService = userService;
        this.ownerRepository = ownerRepository;
        this.propertyRepository = propertyRepository;
        this.leaseAgreementRepository = leaseAgreementRepository;
        this.bankRepository = bankRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.propertyTypeRepository = propertyTypeRepository;
    }

    @GetMapping("{username}")
    public String getUserInfos(@PathVariable("username") String username){

        System.out.println("getuserInfos: ");

        UserDto user = userService.getUserDtoByUsername(username);

        try {
            Optional<Owner> ownerOptional  = ownerRepository.findById(user.getOwnerId());
            Owner owner = ownerOptional.get();
            System.out.println("owner: " + owner);

            List<Transaction_type> transaction_types = transactionTypeRepository.findAll();
            System.out.println("transaction_types: " + transaction_types);

            List<Property_type> property_types = propertyTypeRepository.findAll();
            System.out.println("property_types: " + property_types);

            List<Property> properties = propertyRepository.findAllByOwner_OwnerId(user.getOwnerId());
            System.out.println("property: " + properties);

            List<Bank> bank = bankRepository.findAllByOwner_OwnerId(user.getOwnerId());
            System.out.println("bank: " + bank);

            Map<String, Object> map = new HashMap<>();
            map.put("user", user);
            map.put("owner", owner);
            map.put("transaction_types", transaction_types);
            map.put("properties",properties);
            map.put("bankEntries", bank);
            map.put("property_types", property_types);

            String json = new ObjectMapper().writeValueAsString(map);

            return json;

        } catch (NoSuchElementException | JsonProcessingException ex){
            log.error(ex.toString());
        }


        return "";
    }
}
