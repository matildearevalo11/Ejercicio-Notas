package com.apinotas.notas;

import com.apinotas.notas.seeders.Seeder;
import com.apinotas.notas.services.EnrollmentService;
import com.apinotas.notas.services.StudentService;
import com.apinotas.notas.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@SpringBootApplication
public class NotasApplication {

	private final Seeder seeder;
	private final EnrollmentService enrollmentService;
	private final StudentService studentService;
	private final SubjectService subjectService;

	public static void main(String[] args) {
		SpringApplication.run(NotasApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			boolean areTablesEmpty =
					enrollmentService.count() == 0 &&
							studentService.count() == 0 &&
							subjectService.count() == 0;

			if (areTablesEmpty) {
				seeder.seed();
			}
		};
	}


}
