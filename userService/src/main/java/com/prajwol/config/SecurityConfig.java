package com.prajwol.config;

import com.prajwol.userservice.EmployerDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@EnableWebSecurity
@Configuration
@Log4j2
@Order(1)
public class SecurityConfig {
    private JwtAuthFilter jwtAuthFilter;

    private EmployerDetailsService employerDetailsService;


    @Autowired
    public SecurityConfig(JwtAuthFilter jwtAuthFilter, EmployerDetailsService employerDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.employerDetailsService = employerDetailsService;

    }

    @Bean
    public SecurityFilterChain employerSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("ENTERED CHAIN 1");
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
//                .cors(cors -> cors
//                        .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
//                )
                .authorizeHttpRequests(auth -> auth.requestMatchers("/employer/login", "/employer/register", "/employer/webhooks/stripe", "/employer/pay/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(employerAuthProvider())
                .authenticationManager(employerAuthenticationManager())
                .securityMatcher("/employer/**")
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider employerAuthProvider() {
        System.out.println("I ENTERED THE provider employer");
        log.info("ENTERED provider 1");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(employerDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
//    @Qualifier("employerAuthenticationManager")
    public AuthenticationManager employerAuthenticationManager() {
        return new ProviderManager(List.of(employerAuthProvider()));
    }
}
