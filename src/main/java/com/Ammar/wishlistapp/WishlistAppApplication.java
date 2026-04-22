package com.Ammar.wishlistapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;


@SpringBootApplication
public class WishlistAppApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(WishlistAppApplication.class, args);
    }

}
