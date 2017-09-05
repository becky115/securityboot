package com.becky.securityboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.becky.mappers")
public class SecuritybootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuritybootApplication.class, args);
	}
}
