package com.apinotas.notas.controllers;
import com.apinotas.notas.services.SubjectService;
import com.apinotas.notas.services.dtos.response.SubjectsWithStudentsResponseDTO;
import com.apinotas.notas.utils.PdfUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

    @GetMapping("/download-subjects-pdf")
    public void downloadSubjectsPdf(HttpServletResponse response) throws IOException {
        List<SubjectsWithStudentsResponseDTO> subjectsWithStudents = subjectService.getAllSubjectsWithStudents();
        ByteArrayOutputStream pdfStream = PdfUtils.generatePdfStream(subjectsWithStudents);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"subjects_with_students.pdf\"");

        response.getOutputStream().write(pdfStream.toByteArray());
        response.getOutputStream().flush();
    }
}
