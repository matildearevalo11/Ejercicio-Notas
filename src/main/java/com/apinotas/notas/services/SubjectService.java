package com.apinotas.notas.services;
import com.apinotas.notas.entities.Subject;
import com.apinotas.notas.repositories.SubjectRepository;
import com.apinotas.notas.services.dtos.response.StudentWithGradesResponseDTO;
import com.apinotas.notas.services.dtos.response.SubjectsWithStudentsResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final EnrollmentService enrollmentService;


    public List<SubjectsWithStudentsResponseDTO> getAllSubjectsWithStudents() {
        List<Subject> subjects = subjectRepository.findAll();

        return subjects.stream()
                .map(subject -> {
                    // Obtener la lista de estudiantes con sus calificaciones utilizando el método existente
                    List<StudentWithGradesResponseDTO> students = enrollmentService.getStudentsWithGrades(subject.getId());

                    return new SubjectsWithStudentsResponseDTO(
                            subject.getName(),
                            students
                    );
                })
                .toList();
    }

    public Optional<Subject> findById(Long id){
        return subjectRepository.findById(id);
    }

    public long count() {
        return subjectRepository.count();
    }
}