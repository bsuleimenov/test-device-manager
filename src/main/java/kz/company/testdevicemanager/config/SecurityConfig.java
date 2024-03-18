package kz.company.testdevicemanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Configure an embedded H2 database for storing user details.
     * @return EmbeddedDatabase object
     */
    @Bean
    EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("devicedb")
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    /**
     * Configure a JdbcUserDetailsManager bean to manage user details.
     * Creates three sample users with encoded passwords and roles.
     * @param dataSource DataSource for accessing the user database
     * @param encoder PasswordEncoder for encoding passwords
     * @return JdbcUserDetailsManager instance
     */
    @Bean
    JdbcUserDetailsManager users(DataSource dataSource, PasswordEncoder encoder) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        UserDetails user1 = User.builder()
                .username("user1")
                .password(encoder.encode("user1"))
                .roles("TESTER")
                .build();
        jdbcUserDetailsManager.createUser(user1);
        UserDetails user2 = User.builder()
                .username("user2")
                .password(encoder.encode("user2"))
                .roles("TESTER")
                .build();
        jdbcUserDetailsManager.createUser(user2);
        UserDetails user3 = User.builder()
                .username("user3")
                .password(encoder.encode("user3"))
                .roles("TESTER")
                .build();
        jdbcUserDetailsManager.createUser(user3);
        return jdbcUserDetailsManager;
    }

    /**
     * Configure security filters and authorization rules.
     * @param http HttpSecurity object for configuring security
     * @return SecurityFilterChain instance
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeRequests( auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated())
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Configure a BCryptPasswordEncoder bean for encoding passwords.
     * @return PasswordEncoder instance
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}