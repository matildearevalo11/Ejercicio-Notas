package com.apinotas.notas.services;

import com.apinotas.notas.entities.Student;
import com.apinotas.notas.repositories.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public Student findByCode(Long code){
        return studentRepository.findByCode(code);
    }

    public long count() { return studentRepository.count();
    }
}
