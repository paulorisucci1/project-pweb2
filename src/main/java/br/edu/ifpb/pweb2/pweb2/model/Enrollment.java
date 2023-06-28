package br.edu.ifpb.pweb2.pweb2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Column(
            nullable = false
    )
    @NotNull(message = "The receipt date must be informed")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    @Lob
    private byte[] enrollmentDocument;

    private String fileName;

    @ManyToOne(fetch = FetchType.EAGER)
    private Student student;

    @ManyToOne
    @NotNull(message = "The academic term must be provided. If there are no academic terms available, please register one for your institution")
    private AcademicTerm academicTerm;

    public void setEnrollmentDocument(MultipartFile document) {
        try {
            this.enrollmentDocument = document.getBytes();
            this.fileName = document.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Enrollment enrollment) {
        this.receiptDate = enrollment.getReceiptDate();
        this.academicTerm = enrollment.getAcademicTerm();
        this.enrollmentDocument = enrollment.getEnrollmentDocument();
        this.fileName = enrollment.getFileName();
    }

    public boolean isCurrentStudentEnrollment() {
        if(Objects.nonNull(this.student)) {
            return Objects.equals(this.student.getCurrentEnrollment(), this);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "idEnrollment=" + idEnrollment +
                ", receiptDate=" + receiptDate +
                ", document='" + enrollmentDocument + '\'' +
                '}';
    }
}
