//package com.podzilla.warehouse.Config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(csrf -> csrf.disable()) // Disable CSRF for now (you can enable later if needed)
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/warehouse/packagers/**", "/warehouse/stocks/**")
//                        .hasAnyRole("ASSIGNER", "PACKAGER", "MANAGER") // Assigner, Packager, and Manager can access these endpoints
//                        .requestMatchers("/warehouse/**")
//                        .hasRole("MANAGER") // Only Manager can access all other warehouse APIs
//                        .anyRequest()
//                        .authenticated() // All other requests require authentication
//                )
//                .httpBasic(Customizer.withDefaults()).build(); // HTTP Basic authentication, no need for Customizer.withDefaults()
//
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails manager = User.builder()
//                .username("manager")
//                .password("{noop}managerpass")
//                .roles("MANAGER")
//                .build();
//
//        UserDetails assigner = User.builder()
//                .username("assigner")
//                .password("{noop}assignerpass")
//                .roles("ASSIGNER")
//                .build();
//
//        UserDetails packager = User.builder()
//                .username("packager")
//                .password("{noop}packagerpass")
//                .roles("PACKAGER")
//                .build();
//
//        return new InMemoryUserDetailsManager(manager, assigner, packager);
//    }
//}
