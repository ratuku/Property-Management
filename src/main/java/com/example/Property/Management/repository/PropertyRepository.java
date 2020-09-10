package com.example.Property.Management.repository;

import com.example.Property.Management.entity.Owner;
import com.example.Property.Management.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property,Long> {

    public Optional<Property> findByOwner_Owner_id(Long id);

}
