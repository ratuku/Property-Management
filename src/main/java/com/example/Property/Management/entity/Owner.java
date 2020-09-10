package com.example.Property.Management.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long owner_id;

    private String name;
    private String surname;
    private String phonenumber;
}
