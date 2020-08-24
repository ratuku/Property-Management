package com.example.Property.Management.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Transaction_type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transaction_type_id;

    private String name;

    private Boolean isincome;

}
