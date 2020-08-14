package com.example.Property.Management.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Property_type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Property_type_id;

    private String name;

}