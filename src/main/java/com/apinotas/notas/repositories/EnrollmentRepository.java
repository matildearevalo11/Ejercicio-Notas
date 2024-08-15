package com.apinotas.notas.repositories;
import com.apinotas.notas.entities.Enrollment;
import com.apinotas.notas.entities.Student;
import com.apinotas.notas.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByStudentAndSubject(Student student, Subject subject);
    List<Enrollment> findBySubjectId(Long idSubject);

    @Override
    List<Enrollment> findAll();
}
