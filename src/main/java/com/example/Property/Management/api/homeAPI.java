package com.example.Property.Management.api;

import com.example.Property.Management.auth.UserService;
import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.*;
import com.example.Property.Management.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    public String getUserInfos(@PathVariable("useranme") String username){

        UserDto user = userService.getUserDtoByUsername(username);

        Optional<Owner> ownerOptional  = ownerRepository.findById(user.getOwnerId());
        Owner owner = ownerOptional.get();
        System.out.println("owner: " + owner);

        Optional<Property> propertyOptional = propertyRepository.findByOwner_Owner_id(user.getOwnerId());
        Property property = propertyOptional.get();
        System.out.println("property: " + property);

        Optional<Bank> bankOptional = bankRepository.findByOwner_Owner_id(user.getOwnerId());
        Bank bank = bankOptional.get();
        System.out.println("bank: " + bank);

        List<Transaction_type> transaction_types = transactionTypeRepository.findAll();
        System.out.println("transaction_types: " + transaction_types);

        List<Property_type> property_types = propertyTypeRepository.findAll();
        System.out.println("property_types: " + property_types);

        return "";
    }
}
