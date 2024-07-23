package com.hb.system.ecommerce.shoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = "com.hb.system.ecommerce.shoes")
@SpringBootApplication
public class SystemEcommerceShoesAppJavaApplication  {

	public static void main(String[] args) {
		SpringApplication.run(SystemEcommerceShoesAppJavaApplication.class, args);
    }
}
