package com.example.Property.Management.repository;

import com.example.Property.Management.entity.Owner;
import com.example.Property.Management.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property,Long> {

    public List<Property> findAllByOwner_OwnerId(Long id);

}
