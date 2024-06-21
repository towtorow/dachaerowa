package com.dachaerowa.dachaerowa.domain.service.impl;

import com.dachaerowa.dachaerowa.domain.model.User;
import com.dachaerowa.dachaerowa.domain.repository.UserRepository;
import com.dachaerowa.dachaerowa.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;


    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
