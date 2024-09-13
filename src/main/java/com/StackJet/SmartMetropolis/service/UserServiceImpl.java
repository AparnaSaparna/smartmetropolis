package com.StackJet.SmartMetropolis.service;

import com.StackJet.SmartMetropolis.dto.UserDto;
import com.StackJet.SmartMetropolis.entity.Role;
import com.StackJet.SmartMetropolis.entity.User;
import com.StackJet.SmartMetropolis.repository.RoleRepository;
import com.StackJet.SmartMetropolis.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);

        // Send registration email
        emailService.sendSimpleMessage(user.getEmail(), "Registration Successful", 
                "Dear " + user.getName() + ",\n\nYou have successfully registered.\n\nRegards,\nSmart Metropolis Team");
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        if (name.length > 1) {
            userDto.setLastName(name[1]);
        }
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
