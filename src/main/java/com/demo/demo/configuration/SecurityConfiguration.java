package com.demo.demo.configuration;

import com.demo.demo.auth.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Autowired
    UserDetailsService userDetailsService;

    @Value("${role.NHAN_VIEN}")
    int ROLE_NHAN_VIEN;

    @Value("${role.TRUONG_PHONG}")
    int ROLE_TRUONG_PHONG;

    @Value("${role.PHO_PHONG}")
    int ROLE_PHO_PHONG;

    @Value("${role.ADMIN}")
    int ROLE_ADMIN;

    @Bean
    public AuthenticationManager authenticationManager() {

        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r ->
                        r.requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/customer/**").hasAnyAuthority(String.valueOf(ROLE_TRUONG_PHONG), String.valueOf(ROLE_PHO_PHONG), String.valueOf(ROLE_NHAN_VIEN))
                        .requestMatchers("/api/admin/**").hasAnyAuthority(String.valueOf(ROLE_ADMIN))
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userDetailsService);

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
