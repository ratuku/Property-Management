package com.example.Property.Management.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private long ownerId;

    @NotNull
    private String name;
    @NotNull
    private String surname;
    private String phonenumber;
}
