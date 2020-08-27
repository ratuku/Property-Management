package com.example.Property.Management.auth;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User finByUsername(String username);
}
