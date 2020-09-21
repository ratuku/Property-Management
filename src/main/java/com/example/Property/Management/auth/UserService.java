package com.example.Property.Management.auth;

import com.example.Property.Management.dto.UserDto;
import com.example.Property.Management.entity.ConfirmationToken;
import com.example.Property.Management.service.ConfirmationTokenService;
import com.example.Property.Management.utility.Converter;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Repository
@Transactional
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private ConfirmationTokenService confirmationTokenService;

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

    public Boolean isEmailUnique(String email){
        if (getUserDtoByUsername(email)==null) return true;
        else return false;
    }

    public User saveUser(User user){
        User user1 = userRepository.save(user);
        return  user1;
    }

    public void setUserToken(String token, String username){
        userRepository.setUserJwtToken(token, username);
    }

    public String getUserToken(String username){
        User user = userRepository.findByUsername(username);
        return user.getJwtToken();
    }

    public void confirmUser(ConfirmationToken confirmationToken){

        final User user = confirmationToken.getUser();

        user.setEnabled(true);

        userRepository.save(user);

        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());

    }
}
