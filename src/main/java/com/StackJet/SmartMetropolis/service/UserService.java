package com.StackJet.SmartMetropolis.service;

import java.util.List;

import com.StackJet.SmartMetropolis.dto.UserDto;
import com.StackJet.SmartMetropolis.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
    
    
}