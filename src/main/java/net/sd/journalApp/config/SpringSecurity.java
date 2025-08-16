package net.sd.journalApp.config;

import net.sd.journalApp.service.UserDetialsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity  {

    @Autowired
    private final UserDetialsImp userDetialsImp;

    public SpringSecurity(UserDetialsImp userDetialsImp) {
        this.userDetialsImp = userDetialsImp;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/journal/**").authenticated()
                        .requestMatchers("/user", "/user/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .permitAll() // allow everyone to see login page
                )
                .httpBasic(basic -> {}); // basic auth if needed
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
