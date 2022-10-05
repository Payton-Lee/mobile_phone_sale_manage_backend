package com.phoneshop.shop;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class MobilePhoneSaleApplication {

    public static void main(String[] args) {

        SpringApplication.run(MobilePhoneSaleApplication.class, args);
    }

}
