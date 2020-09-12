package com.example.Property.Management.repository;

import com.example.Property.Management.entity.Bank;
import com.example.Property.Management.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    public List<Bank> findAllByOwner_OwnerId(Long id);
}
