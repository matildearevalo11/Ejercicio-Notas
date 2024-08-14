package com.apinotas.notas.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "written")
@Entity
public class Written {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_written", nullable = false)
    long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "score")
    BigDecimal score;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id_subject", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(referencedColumnName = "code_student", nullable = false)
    private Student student;


}
