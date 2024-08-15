package com.apinotas.notas.repositories;
import com.apinotas.notas.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByCode(long code);
}
