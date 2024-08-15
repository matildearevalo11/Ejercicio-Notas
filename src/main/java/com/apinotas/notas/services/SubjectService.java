package com.apinotas.notas.services;

import com.apinotas.notas.entities.Enrollment;
import com.apinotas.notas.entities.Subject;
import com.apinotas.notas.repositories.SubjectRepository;
import com.apinotas.notas.services.dtos.response.StudentWithGradesResponseDTO;
import com.apinotas.notas.services.dtos.response.SubjectsWithStudentsResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final EnrollmentService enrollmentService;


    public List<SubjectsWithStudentsResponseDTO> getAllSubjectsWithStudents() {
        List<Subject> subjects = subjectRepository.findAll();

        return subjects.stream()
                .map(subject -> {
                    List<Enrollment> enrollments = enrollmentService.findBySubjectId(subject.getId());
                    int maxGrades = enrollments.stream()
                            .mapToInt(enrollment -> enrollment.getGrades().size())
                            .max()
                            .orElse(0);
                    List<StudentWithGradesResponseDTO> students = enrollments.stream()
                            .map(enrollment -> {
                                List<Double> grades = new ArrayList<>(enrollment.getGrades());
                                while (grades.size() < maxGrades) {
                                    grades.add(0.0);
                                }
                                return new StudentWithGradesResponseDTO(
                                        enrollment.getStudent().getName(),
                                        grades,
                                        enrollment.getAverage(maxGrades)
                                );
                            })
                            .toList();

                    return new SubjectsWithStudentsResponseDTO(
                            subject.getName(),
                            students
                    );
                })
                .toList();
    }
}