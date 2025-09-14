package com.econ.edge_news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EdgeNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdgeNewsApplication.class, args);
	}

}
