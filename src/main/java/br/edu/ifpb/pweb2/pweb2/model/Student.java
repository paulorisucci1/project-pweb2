package br.edu.ifpb.pweb2.pweb2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_institution")
    private Institution institution;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Enrollment> enrollments;

    @OneToOne(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Enrollment currentEnrollment;

    public void update(Student updatedStudent) {
        this.name = updatedStudent.getName();
        this.registration = updatedStudent.getRegistration();
        this.institution = updatedStudent.getInstitution();
        this.currentEnrollment = updatedStudent.getCurrentEnrollment();
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
        this.currentEnrollment = enrollment;
        enrollment.setStudent(this);
    }

    public void removeAllEnrollments() {
        this.enrollments.forEach(enrollment -> enrollment.setStudent(null));
        this.enrollments = new ArrayList<>();
        this.currentEnrollment = null;
    }

    public void removeEnrollment(Enrollment enrollment) {
        if(isCurrentEnrollment(enrollment)) {
            this.currentEnrollment = null;
        }
        enrollment.setStudent(null);
        this.enrollments.remove(enrollment);
    }

    private boolean isCurrentEnrollment(Enrollment enrollment) {
        return Objects.equals(enrollment, this.currentEnrollment);
    }
}