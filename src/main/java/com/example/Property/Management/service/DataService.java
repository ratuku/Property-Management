package com.example.Property.Management.service;

import com.example.Property.Management.auth.User;
import com.example.Property.Management.auth.UserService;
import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.*;
import com.example.Property.Management.jwt.UsernameAndPasswordAuthenticationRequest;
import com.example.Property.Management.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class DataService {

    UserService userService;
    Transaction_typeRepository transactionTypeRepository;
    Property_typeRepository propertyTypeRepository;
    BankRepository bankRepository;
    PropertyRepository propertyRepository;
    OwnerRepository ownerRepository;

    @Autowired
    public DataService(UserService userService, Transaction_typeRepository transactionTypeRepository,
                       Property_typeRepository propertyTypeRepository, BankRepository bankRepository,
                       PropertyRepository propertyRepository, OwnerRepository ownerRepository) {
        this.userService = userService;
        this.transactionTypeRepository = transactionTypeRepository;
        this.propertyTypeRepository = propertyTypeRepository;
        this.bankRepository = bankRepository;
        this.propertyRepository = propertyRepository;
        this.ownerRepository = ownerRepository;
    }

    public UsernameAndPasswordAuthenticationRequest registerUser( RegistrationForm form){

        log.info("form: " + form);
        Owner owner = form.getOwner();
        owner = ownerRepository.save(owner);

        User user = form.getUser();
        String originalPassword = user.getPassword();
        user.encodePassword();
        user.setOwner(owner);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user = userService.saveUser(user);

        return new UsernameAndPasswordAuthenticationRequest(user.getUsername(), originalPassword);
        }

    public String getUserFullInfo(String username){
        UserDto user = userService.getUserDtoByUsername(username);

        try {
            Optional<Owner> ownerOptional  = ownerRepository.findById(user.getOwnerId());
            Owner owner = ownerOptional.get();

            List<Transaction_type> transaction_types = transactionTypeRepository.findAll();

            List<Property_type> property_types = propertyTypeRepository.findAll();

            List<Property> properties = propertyRepository.findAllByOwner_OwnerId(user.getOwnerId());

            List<Bank> bank = bankRepository.findAllByOwner_OwnerId(user.getOwnerId());

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

    public void setUserToken(String token, String username){
        userService.setUserToken(token, username);
    }

    public String getUserToken(String username){
        String token = userService.getUserToken(username);
        return token;
    }

}
