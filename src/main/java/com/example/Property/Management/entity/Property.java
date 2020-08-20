package com.example.Property.Management.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long property_id;

    @OneToOne(targetEntity = Property_type.class)
    private Property_type property_type;

/*    @OneToMany(targetEntity = Lease_agreement.class)
    private List<Lease_agreement> lease_agreement;*/

    private String town;

    private String suburb;

    private String street_namenumber;

    private String estate_name;

    private int apartment_number;

    private String postalcode;

    private int number_of_toilets;

    private int number_of_bath;

    private int number_of_shower;

    private int number_of_kitchen;

    private int number_of_living_rooms;

    private String description;

}
