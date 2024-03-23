package com.example.configuration;

import com.example.controller.UserController;
import com.example.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan(basePackages = "com.example.resources")
public class Launcher {
    public static void main(String[] args) {
        System.out.println("Run!");
        UserController userController = new UserController();

        SpringApplication.run(Launcher.class, args);
    }
}
