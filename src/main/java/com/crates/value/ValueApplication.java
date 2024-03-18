package com.crates.value;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ValueApplication {

	public static void main(String[] args) {

		System.out.println("hello world");

		SpringApplication.run(ValueApplication.class, args);
	}

}
