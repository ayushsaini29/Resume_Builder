package com.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.auth.dao.UserRepository;
import com.auth.dto.UserRegistrationDto;
import com.auth.entity.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    public void registerUser(UserRegistrationDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        userRepository.save(user);

        switch (user.getRole()) {
            case "ROLE_Staff":
                restTemplate.postForEntity("http://staff-service/api/staff/register", userDto, String.class);
                break;
            case "ROLE_Student":
                restTemplate.postForEntity("http://student-service/api/student/register", userDto, String.class);
                break;
            case "ROLE_Parent":
                restTemplate.postForEntity("http://parent-service/api/parent/register", userDto, String.class);
                break;
            default:
                throw new IllegalStateException("Unexpected role: " + user.getRole());
        }
    }

    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Generate JWT token and return
            return "JWT_TOKEN"; // Replace with actual token generation logic
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Send password reset email
        } else {
            throw new IllegalArgumentException("Email not found");
        }
    }
}

