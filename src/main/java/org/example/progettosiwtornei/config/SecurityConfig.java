package org.example.progettosiwtornei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Collegamento al database, usato per leggere username, password e ruolo
    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Dice a Spring Security come trovare gli utenti nel database
    @Bean
    public UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        // Query per recuperare username e password dell'utente che fa login
        manager.setUsersByUsernameQuery(
                "SELECT username, password, 1 as enabled " +
                        "FROM utente " +
                        "WHERE username = ?"
        );

        // Query per recuperare il ruolo dell'utente, tipo USER o ADMIN
        manager.setAuthoritiesByUsernameQuery(
                "SELECT username, ruolo_utente " +
                        "FROM utente " +
                        "WHERE username = ?"
        );

        return manager;
    }

    // Serve per gestire password codificate con BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Qui si decidono le regole di accesso alle pagine
    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        httpSecurity.authorizeHttpRequests(authorize -> {

            // Endpoint usati da React
            authorize.requestMatchers("/api/**").permitAll();

            // Pagine pubbliche
            authorize.requestMatchers(HttpMethod.GET,
                    "/",
                    "/index",
                    "/login",
                    "/home",
                    "/registrazione",
                    "/listaPartite",
                    "/partita/**",
                    "/listaGiocatori",
                    "/listaArbitri",
                    "/arbitro/**",
                    "/listaSquadre",
                    "/squadra/**",
                    "/listaTornei",
                    "/listaTornei/**",
                    "/giocatore/**",
                    "/torneo/**",
                    "/css/**",
                    "/images/**",
                    "/favicon.ico"
            ).permitAll();

            authorize.requestMatchers(HttpMethod.POST, "/login", "/registrazione").permitAll();

            authorize.requestMatchers(HttpMethod.GET, "/home").authenticated();

            authorize.requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority("ADMIN");
            authorize.requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority("ADMIN");

            // tutto il resto richiede il login
            authorize.anyRequest().authenticated();
        });

        // Configurazione login
        httpSecurity.formLogin(form -> {
            form.loginPage("/login").permitAll();
            form.defaultSuccessUrl("/home", true);   // dopo il login corretto --> vai a home
            form.failureUrl("/login?error=true");
        });

        // Configurazione logout
        httpSecurity.logout(logout -> {
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/");
            logout.invalidateHttpSession(true);
            logout.clearAuthentication(true);
            logout.permitAll();
        });

        return httpSecurity.build();
    }

    // Serve per permettere a React, che gira su localhost:5173, di chiamare Spring, che gira su localhost:8080
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}