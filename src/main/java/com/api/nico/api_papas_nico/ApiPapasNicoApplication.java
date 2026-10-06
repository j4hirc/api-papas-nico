package com.api.nico.api_papas_nico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ApiPapasNicoApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Guayaquil"));
		SpringApplication.run(ApiPapasNicoApplication.class, args);
	}

}
