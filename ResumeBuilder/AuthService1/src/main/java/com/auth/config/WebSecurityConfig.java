package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.auth.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    @Autowired
    private UserService userService;
    
//    @Autowired
//    private AuthenticationManager authenticationManager;
    
//    @Autowired
//    private UserDetailsService userDetailsService;

//    @Override
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
	    	.csrf(csrf -> csrf.disable())
	    	.cors(cors -> cors.disable())
            .authorizeHttpRequests(authorizeRequests ->
            		authorizeRequests
                    .requestMatchers("/api/auth/**").permitAll()  
                    .requestMatchers("/admin/**").hasRole("ADMIN")  
                    .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")  
                    .requestMatchers("/register").permitAll()
                    .anyRequest().authenticated() 
                )
                .formLogin(formLogin -> formLogin
                    .loginProcessingUrl("/api/auth/login")  
                    .defaultSuccessUrl("/home", true)  
                )
                .logout(logout -> logout
                    .logoutUrl("/api/auth/logout")  
                    .logoutSuccessUrl("/api/auth/login")  
                )
                .httpBasic(httpBasic -> httpBasic
                	.disable());

            return http.build();
        }

////    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//    }
//    
    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();  // Use BCryptPasswordEncoder for password encoding
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
}
