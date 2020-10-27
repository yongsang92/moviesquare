package com.moviesquare.japan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class JapanApplication {

	public static void main(String[] args) {
		SpringApplication.run(JapanApplication.class, args);
	}

}
