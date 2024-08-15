package com.apinotas.notas;

import com.apinotas.notas.seeders.Seeder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@SpringBootApplication
public class NotasApplication {

	private final Seeder seeder;

	public static void main(String[] args) {
		SpringApplication.run(NotasApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(){
		return args -> seeder.seed();
	}

}
