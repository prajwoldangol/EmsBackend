package com.prajwol.config;

import com.prajwol.userservice.EmployeeDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@Log4j2
@Order(2)
public class EmployeeSecurityConfig {
    private JwtAuthFilter jwtAuthFilter;

    private EmployeeDetailsService employeeDetailsService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public EmployeeSecurityConfig(JwtAuthFilter jwtAuthFilter, EmployeeDetailsService employeeDetailsService,PasswordEncoder passwordEncoder) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.employeeDetailsService = employeeDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public SecurityFilterChain employeeSecurityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("I ENTERED THE LAST CHAIN");
        log.info("ENTERED CHAIN 2");
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/employee/login", "/employee/register", "/employee/password/*").permitAll()
                        .anyRequest().authenticated()

                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(employeeAuthProvider())
                .authenticationManager(employeeAuthenticationManager())
                .securityMatcher("/employee/**")
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider employeeAuthProvider() {
        System.out.println("I ENTERED THE provider employee");
        log.info("ENTERED provider 2");
        DaoAuthenticationProvider authProviders = new DaoAuthenticationProvider();
        authProviders.setUserDetailsService(employeeDetailsService);
        authProviders.setPasswordEncoder(passwordEncoder);
        return authProviders;
    }
//
//    @Bean
//    public PasswordEncoder pwEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
//    @Qualifier("employeeAuthenticationManager")
    public AuthenticationManager employeeAuthenticationManager() {
        return new ProviderManager(List.of(employeeAuthProvider()));
    }

}
