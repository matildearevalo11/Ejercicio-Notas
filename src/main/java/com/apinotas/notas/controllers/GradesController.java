package com.apinotas.notas.controllers;
import com.apinotas.notas.services.EnrollmentService;
import com.apinotas.notas.services.dtos.response.MessageResponseDTO;
import com.apinotas.notas.services.dtos.response.StudentWithGradesResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/grades")
public class GradesController {


    private final EnrollmentService enrollmentService;

    @PostMapping("/upload")
    public MessageResponseDTO uploadGrades(@RequestParam("idSubject") Long idSubject, @RequestParam("file") MultipartFile file) {
        try {
            enrollmentService.uploadGrades(file, idSubject);
            return new MessageResponseDTO("File uploaded successfully.");
        } catch (IOException e) {
            return new MessageResponseDTO("Error uploading file.");
        }
    }

    @GetMapping()
    public List<StudentWithGradesResponseDTO> studentGrades(@RequestParam Long idSubject){
        return enrollmentService.getStudentsWithGrades(idSubject);
    }
}
