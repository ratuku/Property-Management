package com.example.Property.Management.repository;

import com.example.Property.Management.entity.Property_type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface Property_typeRepository extends JpaRepository<Property_type, Long> {
}
