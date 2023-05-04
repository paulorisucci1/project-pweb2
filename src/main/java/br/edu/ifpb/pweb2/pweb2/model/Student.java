package br.edu.ifpb.pweb2.pweb2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(exclude = "currentEnrollment")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStudent;

    @Column(
            length = 100,
            nullable = false
    )
    @Size(min=3, max=100, message = "The student name must have between 3 and 100 characters")
    @NotNull(message = "The name must not be null")
    private String name;

    @Column(
            length = 11,
            nullable = false,
            unique = true
    )
    @Size(min=11, max=11, message = "The registration must have exactly 11 characters")
    @NotNull(message = "The registration must not be null.")
    private String registration;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_institution")
    private Institution institution;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    private List<Enrollment> enrollments;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Enrollment currentEnrollment;

    public void update(Student updatedStudent) {
        this.name = updatedStudent.getName();
        this.registration = updatedStudent.getRegistration();
        this.institution = updatedStudent.getInstitution();
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
        this.currentEnrollment = enrollment;
        enrollment.setStudent(this);
    }
}