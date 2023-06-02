package br.edu.ifpb.pweb2.pweb2.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date receiptDate;

    @Lob
    @ToString.Exclude
    private byte[] document;

    @ManyToOne(fetch = FetchType.EAGER)
    private Student student;

    @ManyToOne
    private AcademicTerm academicTerm;

    public void update(Enrollment enrollment) {
        this.receiptDate = enrollment.getReceiptDate();
        this.document = enrollment.getDocument();
        this.academicTerm = enrollment.getAcademicTerm();
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "idEnrollment=" + idEnrollment +
                ", receiptDate=" + receiptDate +
                '}';
    }
}
