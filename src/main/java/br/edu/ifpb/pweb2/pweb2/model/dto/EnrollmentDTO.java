package br.edu.ifpb.pweb2.pweb2.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@EqualsAndHashCode(exclude = "studentDTO")
@ToString(exclude = "document")
public class EnrollmentDTO {

    private Integer idEnrollment;

    @NotNull(message = "The receipt date must be informed")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date receiptDate;

    private byte[] document;

    private StudentDTO student;

    @NotNull(message = "The academic term must be provided. If there are no academic terms available, please register one for your institution")
    private AcademicTermDTO academicTerm;

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public void setDocument(MultipartFile file) {
        try {
            this.document = file.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
