package com.Ammar.wishlistapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class WishlistAppApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(WishlistAppApplication.class, args);
    }

}
