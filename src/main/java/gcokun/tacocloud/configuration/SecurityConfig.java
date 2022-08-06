package gcokun.tacocloud.configuration;

import gcokun.tacocloud.authentication.UserInformation;
import gcokun.tacocloud.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            UserInformation userInformation = userRepository.findUserByUsername(username);
            if (userInformation != null) return userInformation;
            throw new UsernameNotFoundException("User '" + username + "' not found!");
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        //no need for ROLE_ prefix, it will be assumed
        return httpSecurity
                //For testing
                .csrf().disable()

                .authorizeRequests()
                .mvcMatchers("/design", "/orders").access("hasRole('USER')")
                .mvcMatchers("/", "/**").access("permitAll()")

                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/design")

                .and()
                .logout()
                .logoutSuccessUrl("/")

                //.and()
                //.csrf()
                //.ignoringAntMatchers("/h2-console/**")

                // Allow pages to be loaded in frames from the same origin; needed for H2-Console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .build();
    }
}
