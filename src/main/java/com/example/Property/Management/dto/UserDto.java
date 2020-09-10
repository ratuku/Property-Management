package com.example.Property.Management.dto;

import com.example.Property.Management.entity.Owner;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToOne;

@Data
public class UserDto {

    private String username;

    private long ownerId;
}
