package org.example.ecomerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EComerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EComerceApplication.class, args);
    }

}
