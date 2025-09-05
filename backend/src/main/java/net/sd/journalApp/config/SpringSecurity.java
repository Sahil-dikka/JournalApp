package net.sd.journalApp.config;

import net.sd.journalApp.filter.JWTFilter;
import net.sd.journalApp.service.UserDetialsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    private final UserDetialsImp userDetialsImp;
    private final JWTFilter jwtFilter;

    @Autowired
    public SpringSecurity(UserDetialsImp userDetialsImp, JWTFilter jwtFilter) {
        this.userDetialsImp = userDetialsImp;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetialsImp);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable()) // disable CSRF for APIs
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // stateless for JWT
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/journal/**").authenticated()
                        .requestMatchers("/user", "/user/**").permitAll() // allow registration/login
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                );

        // Add JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
