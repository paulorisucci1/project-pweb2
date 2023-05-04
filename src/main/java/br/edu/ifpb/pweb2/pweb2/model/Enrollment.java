package br.edu.ifpb.pweb2.pweb2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode(exclude = "student")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEnrollment;

    @Column(
            nullable = false
    )
    @NotNull(message = "The receipt date must be informed")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date receiptDate;

    @Size(max = 255, message = "The note must have a maximum of 255 characters")
    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    private Student student;

    @ManyToOne
    @NotNull(message = "The academic term must be provided. If there are no academic terms available, please register one for your institution")
    private AcademicTerm academicTerm;

    public void update(Enrollment enrollment) {
        this.receiptDate = enrollment.getReceiptDate();
        this.note = enrollment.getNote();
        this.academicTerm = enrollment.getAcademicTerm();
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "idEnrollment=" + idEnrollment +
                ", receiptDate=" + receiptDate +
                ", note='" + note + '\'' +
                '}';
    }
}
