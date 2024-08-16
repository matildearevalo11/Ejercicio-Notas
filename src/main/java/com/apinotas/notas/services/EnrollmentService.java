package com.apinotas.notas.services;
import com.apinotas.notas.entities.Enrollment;
import com.apinotas.notas.entities.Student;
import com.apinotas.notas.entities.Subject;
import com.apinotas.notas.exceptions.NotFoundException;
import com.apinotas.notas.repositories.EnrollmentRepository;
import com.apinotas.notas.repositories.SubjectRepository;
import com.apinotas.notas.services.dtos.response.StudentWithGradesResponseDTO;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UploadFileService uploadFileService;
    private final StudentService studentService;
    private final SubjectRepository subjectRepository;
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentService.class);

    public void uploadGrades(MultipartFile file, Long idSubject) throws IOException {
        if (file.isEmpty()) {
            throw new NotFoundException("The file is empty.");
        }
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            if (iterator.hasNext()) {
                iterator.next();
            }

            while (iterator.hasNext()) {
                Row row = iterator.next();
                Long code = uploadFileService.getLongFromCell(row.getCell(0));
                List<Double> grades = new ArrayList<>();

                for (int i = 1; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        Double grade = uploadFileService.getNumericFromCell(cell);
                        if (grade != null) {
                            grades.add(grade);
                        }
                    }
                }

                try {
                    updateGrades(code, idSubject, grades);
                } catch (NotFoundException e) {
                    logger.error("Error updating grades: {}", e.getMessage());
                }
            }
        }
    }
    public void updateGrades(Long code, Long idSubject, List<Double> grades) {
        Student student = studentService.findByCode(code);
        if (student == null) {
            throw new NotFoundException("Student with code "+ code +" not found.");
        }

        Subject subject = subjectRepository.findById(idSubject)
                .orElseThrow(() -> new NotFoundException("Subject not found."));

        Enrollment enrollment = enrollmentRepository.findByStudentAndSubject(student, subject)
                .orElseThrow(() -> new NotFoundException("The student is not enrolled in the subject."));

        enrollment.setGrades(grades);
        enrollmentRepository.save(enrollment);
    }


    public List<StudentWithGradesResponseDTO> getStudentsWithGrades(Long idSubject) {
        List<Enrollment> enrollments = enrollmentRepository.findBySubjectId(idSubject);
        int maxGrades = enrollments.stream()
                .mapToInt(enrollment -> enrollment.getGrades().size())
                .max()
                .orElse(0);

        return enrollments.stream().map(enrollment -> {
                    StudentWithGradesResponseDTO students = new StudentWithGradesResponseDTO();
                    students.setName(enrollment.getStudent().getName());
                    List<Double> grades = new ArrayList<>(enrollment.getGrades());
                    while (grades.size() < maxGrades) {
                        grades.add(0.0);
                    }

                    students.setGrades(grades);
                    students.setAverage(enrollment.getAverage(maxGrades));
                    return students;
                }).sorted((dto1, dto2) -> Double.compare(dto2.getAverage(), dto1.getAverage()))
                .toList();
    }

    public long count() {
        return enrollmentRepository.count();
    }
}

