package com.example.Property.Management.auth;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    @Modifying
    @Query(value = "update User u set u.jwtToken = ? where u.username = ?",
            nativeQuery = true)
    void setUserJwtToken(String userJwtToken, String username);
}
