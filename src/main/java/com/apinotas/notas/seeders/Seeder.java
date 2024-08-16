package com.apinotas.notas.seeders;
import com.apinotas.notas.entities.Student;
import com.apinotas.notas.entities.Subject;
import com.apinotas.notas.entities.Enrollment;
import com.apinotas.notas.repositories.StudentRepository;
import com.apinotas.notas.repositories.SubjectRepository;
import com.apinotas.notas.repositories.EnrollmentRepository;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class Seeder {

    Faker faker = new Faker();
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final EnrollmentRepository enrollmentRepository;

    FakeValuesService fakeValuesService = new FakeValuesService(
            new Locale("en-GB"), new RandomService());

    Random random=new Random();
    int numStudents=30;
    int numSubjects=3;




    public void seed() {
        List<Student> students = seedStudents();
        List<Subject> subjects = seedSubjects();
        seedEnrollments(students, subjects);
    }

    public Student seedStudent(){
        String name;
        do {
            name = faker.name().fullName();
        } while (subjectRepository.findByName(name).isPresent());
        Student student=new Student();
        student.setName(name);
        return studentRepository.save(student);
    }

    public Subject seedSubject() {
        String name;
        do {
            name = faker.educator().course();
        } while (subjectRepository.findByName(name).isPresent());

        Subject subject = new Subject();
        subject.setName(name);
        return subjectRepository.save(subject);
    }

    public List<Student> seedStudents(){
        List<Student> students=new ArrayList<>();
            for (int i=0;i<numStudents;i++){
                Student student =seedStudent();
                students.add(student);
            }
        return students;
    }

    public List<Subject> seedSubjects() {
        List<Subject> subjects = new ArrayList<>();
        while (subjects.size() < numSubjects) {
            Subject subject = seedSubject();
            subjects.add(subject);
        }
        return subjects;
    }

    private void seedEnrollments(List<Student> students, List<Subject> subjects) {

        for (Subject subject : subjects) {
            int numEnrollments = 11 + random.nextInt(19);

            List<Student> enrolledStudents = new ArrayList<>();
            for (int i = 0; i < numEnrollments; i++) {
                Student student = getRandomStudent(students);
                while (getRandomStudent(students) == null || enrolledStudents.contains(student)) {
                    student = getRandomStudent(students);
                }
                if (getRandomStudent(students) != null) {
                    Enrollment enrollment = new Enrollment();
                    enrollment.setStudent(student);
                    enrollment.setSubject(subject);

                    enrollmentRepository.save(enrollment);
                    enrolledStudents.add(student);
                }
            }
        }
    }

    private Student getRandomStudent(List<Student> students) {
        if (students.isEmpty()) {
            return null;
        }
        int index = random.nextInt(students.size());
        return students.get(index);
    }
}




