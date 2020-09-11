package com.example.Property.Management.utility;

import com.example.Property.Management.auth.User;
import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.Owner;
import org.springframework.beans.BeanUtils;

public class Converter {

    /*public static User dtoToUser(UserDto userDTO){
        User user=new User();
        BeanUtils.copyProperties(user,userDTO);
        return user;
    }*/

    public static UserDto userToDto(User user){
        UserDto userDto=new UserDto();
        //BeanUtils.copyProperties(userDto,user);
        userDto.setUsername(user.getUsername());
        userDto.setOwnerId(user.getOwner().getOwnerId());
        return userDto;
    }

}
