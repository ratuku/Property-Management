package com.example.Property.Management.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Property_type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Property_type_id;

    @Column(unique=true)
    private String name;

}
