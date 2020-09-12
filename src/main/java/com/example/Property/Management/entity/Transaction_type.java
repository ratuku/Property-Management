package com.example.Property.Management.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Transaction_type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long transaction_type_id;

    @Column(unique=true)
    private String name;

    private Boolean isincome;

}
