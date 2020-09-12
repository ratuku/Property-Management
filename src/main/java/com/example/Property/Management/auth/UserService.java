package com.example.Property.Management.auth;

import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.utility.Converter;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;

@Service
@Repository
@Transactional
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Username: " + username);

        User user = userRepository.findByUsername(username);

        System.out.println("User: " + user);

        if (user!=null){
            return user;
        }
        throw new UsernameNotFoundException(
                "User '" + username + "' not found"
        );
    }

    public UserDto getUserDtoByUsername(String email){

        User user= userRepository.findByUsername(email);

        System.out.println("Got user: " + user);

        if (user!= null) {
            UserDto userDto = new Converter().userToDto(user);
            System.out.println("userDto: " + userDto);
            return userDto;
        }
        return null;
    }

    public User saveUser(User user){
        User user1 = userRepository.save(user);
        return  user1;
    }
}
