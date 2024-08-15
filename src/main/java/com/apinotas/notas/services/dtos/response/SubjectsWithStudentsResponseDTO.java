package com.apinotas.notas.services.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectsWithStudentsResponseDTO {
    private String name;
    private List<StudentWithGradesResponseDTO> students;

}
