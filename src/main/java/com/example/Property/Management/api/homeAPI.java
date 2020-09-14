package com.example.Property.Management.api;

import com.example.Property.Management.auth.User;
import com.example.Property.Management.auth.UserService;
import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.*;
import com.example.Property.Management.repository.*;
import com.example.Property.Management.utility.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.XSlf4j;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
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

    @PostMapping("/register")
    public Map<String, Object> addNewUser(@RequestBody RegistrationForm form)  {

        Map<String, Object> mapResponse = new HashMap<>();
        try {
            log.info("form: "+ form);
            Owner owner = form.getOwner();
            owner = ownerRepository.save(owner);
            mapResponse.put("owner", owner);

            User user = form.getUser();
            user.encodePassword();
            user.setOwner(owner);
            user = userService.saveUser(user);
            UserDto userDto = Converter.userToDto(user);
            mapResponse.put("user",userDto);

            return mapResponse;
        } catch (Exception exception){

            log.error(exception.toString());
            mapResponse = new HashMap<>();
            mapResponse.put("Error", new String("Error while trying to save new user."));
            return mapResponse;
        }

    }
}
