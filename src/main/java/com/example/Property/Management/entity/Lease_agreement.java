package com.example.Property.Management.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Lease_agreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long agreenment_id;

    private String expected_rent;
    private String number_of_month;
    private Date datetime;
    private boolean iscurrent;

    @OneToOne(targetEntity = Property.class)
    private Property property_id;

    @PrePersist
    private void initDate(){
        this.datetime = new Date();
    }
}
