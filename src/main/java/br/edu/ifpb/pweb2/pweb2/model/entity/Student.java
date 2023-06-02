package br.edu.ifpb.pweb2.pweb2.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private String name;

    @Column(
            length = 11,
            nullable = false,
            unique = true
    )
    private String registration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_institution")
    private Institution institution;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Enrollment> enrollments;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
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

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", name='" + name + '\'' +
                ", registration='" + registration + '\'' +
                ", institution=" + institution +
                ", currentEnrollment=" + currentEnrollment.toString() +
                '}';
    }
}