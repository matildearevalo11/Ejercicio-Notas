package com.apinotas.notas.controllers;
import com.apinotas.notas.services.SubjectService;
import com.apinotas.notas.services.dtos.response.SubjectsWithStudentsResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/subjects")
public class SubjectsController {

    private final SubjectService subjectService;

    @GetMapping()
    public List<SubjectsWithStudentsResponseDTO> studentGrades(){
        return subjectService.getAllSubjectsWithStudents();
    }

}
