package com.apinotas.notas.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "enrollment")
@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ElementCollection
    @CollectionTable(name = "enrollment_grades", joinColumns = @JoinColumn(name = "enrollment_id"))
    @Column(name = "grade")
    private List<Double> grades = new ArrayList<>();


    public Double getAverage(int maxGrades) {
        List<Double> gradesWithPadding = new ArrayList<>(this.grades);

        while (gradesWithPadding.size() < maxGrades) {
            gradesWithPadding.add(0.0);
        }
        double sum = gradesWithPadding.stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        return sum / maxGrades;
    }
}

