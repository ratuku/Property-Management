package com.example.Property.Management.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String  amount;

    @OneToOne(targetEntity = Owner.class)
    private Owner owner;

    @OneToOne(targetEntity = Transaction_type.class)
    private Transaction_type transaction_type;

    private Date date;

    @PrePersist
    void createdAt(){this.date = new Date();}

}
