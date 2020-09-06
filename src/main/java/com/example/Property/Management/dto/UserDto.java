package com.example.Property.Management.dto;

import com.example.Property.Management.entity.Owner;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToOne;

@Setter
@Getter
public class UserDto {

    private String username;

    @OneToOne(targetEntity = Owner.class)
    private Owner owner;
}
