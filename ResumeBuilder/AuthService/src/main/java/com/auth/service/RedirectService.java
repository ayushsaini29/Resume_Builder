package com.auth.service;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class RedirectService {

    public String determineTargetUrl(Authentication authentication) {
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        switch (role) {
            case "ROLE_Staff":
                return "/staff/dashboard/home";
            case "ROLE_Student":
                return "/student/dashboard/home";
            case "ROLE_Parent":
                return "/parent/dashboard/home";
            default:
                throw new IllegalStateException("Unexpected role: " + role);
        }
    }
}

