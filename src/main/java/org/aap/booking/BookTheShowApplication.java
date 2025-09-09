package org.aap.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookTheShowApplication {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(BookTheShowApplication.class, args);
    }
}