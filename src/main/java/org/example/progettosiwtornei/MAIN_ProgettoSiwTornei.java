package org.example.progettosiwtornei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MAIN_ProgettoSiwTornei {

    public static void main(String[] args) {

        System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("password"));
        SpringApplication.run(MAIN_ProgettoSiwTornei.class, args);
    }
}
