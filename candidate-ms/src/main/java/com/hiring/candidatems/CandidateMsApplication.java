package com.hiring.candidatems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CandidateMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandidateMsApplication.class, args);
	}

}
