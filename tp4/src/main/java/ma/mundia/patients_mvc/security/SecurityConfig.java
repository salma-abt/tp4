package ma.mundia.patients_mvc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder().encode("1234")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder().encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder().encode("1234")).roles("USER", "ADMIN").build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**").hasRole("USER")  // Path accessible only for USER role
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Path accessible only for ADMIN role
                        .anyRequest().authenticated()  // Any request requires authentication
                )
                .formLogin(form -> form
                        .permitAll()
                        .loginPage("/login")
                        .defaultSuccessUrl("/user/index", true)  // Redirect to /user/index after successful login
                        .failureUrl("/login?error=true")  // Redirect to /login on failure
                )
                .exceptionHandling(error ->
                        error.accessDeniedPage("/notAuthorized") // Redirect to /notAuthorized when access is denied
                )
                .rememberMe(r -> r
                        .key("uniqueAndSecret")  // Define a custom key for the "remember me" cookie (optional)
                        .tokenValiditySeconds(86400)  // Set the validity of the "remember me" cookie (e.g., 1 day)
                );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCrypt for password encoding
    }
}
