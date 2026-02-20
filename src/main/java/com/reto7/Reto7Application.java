package com.reto7;

import com.reto7.reto7.Reto7Runner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Reto7Application {

    public static void main(String[] args) {
        SpringApplication.run(Reto7Application.class, args);
    }

    @Bean
    CommandLineRunner runReto7() {
        return args -> Reto7Runner.run();
    }
}