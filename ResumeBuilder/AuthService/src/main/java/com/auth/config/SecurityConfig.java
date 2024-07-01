package com.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth.filter.JwtTokenFilter;
import com.auth.service.RedirectService;

@Configuration
@EnableWebSecurity
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private RedirectService redirectService;

//    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
    	.csrf(csrf -> csrf.disable())
    	.cors(cors -> cors.disable())
        .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                .requestMatchers("/register").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/staff/**").hasRole("STAFF")
                .requestMatchers("/student/**").hasRole("STUDENT")
                .requestMatchers("/parent/**").hasRole("PARENT")
                .anyRequest().authenticated()
        )
        .formLogin(formLogin ->
        	formLogin
        		.loginPage("/login")
        		.loginProcessingUrl("/login")
        		.permitAll()
        		.successHandler(authenticationSuccessHandler())
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtSecret), UsernamePasswordAuthenticationFilter.class)
        		
        );
                
                
        
//        http
//    	.csrf(csrf -> csrf.disable())
//    	.cors(cors -> cors.disable())
//        .authorizeHttpRequests(authorizeRequests ->
//        	authorizeRequests
//            .requestMatchers("/register").permitAll()
//            .requestMatchers("/admin/**").hasRole("ADMIN")
//            .requestMatchers("/staff/**").hasRole("STAFF")
//            .requestMatchers("/student/**").hasRole("STUDENT")
//            .requestMatchers("/parent/**").hasRole("PARENT")
//            .anyRequest().authenticated()
//    )
//    .formLogin(formLogin ->
//        formLogin
//            .loginPage("/login")
//            .permitAll()
//    );
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String targetUrl = redirectService.determineTargetUrl(authentication);
            response.sendRedirect(targetUrl);
        };
    }
}
