package br.edu.ifpb.pweb2.pweb2.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    DataSource dataSource;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorize) -> authorize
                        .requestMatchers("/css/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
        ).formLogin((form) -> form
                .loginPage("/auth")
                .defaultSuccessUrl("/home", true)
                .permitAll())
                .logout((logout -> logout.logoutUrl("/auth/logout")));

        return http.build();
    }

    @Bean
    public UserDetailsManager users() {

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        if(!users.userExists(user.getUsername())) {
            users.createUser(user);
        }
        if(!users.userExists(admin.getUsername())) {
            users.createUser(admin);
        }
        return users;
    }
}
