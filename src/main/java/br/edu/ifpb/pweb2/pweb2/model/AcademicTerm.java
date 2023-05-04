package br.edu.ifpb.pweb2.pweb2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode(exclude = "institution")
public class AcademicTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAcademicTerm;

    @Column(nullable = false)
    @NotNull(message = "The year must be informed")
    private Integer year;

    @Column(nullable = false)
    @NotBlank(message = "The code must be informed")
    private String code;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    @NotNull(message = "The startDate must be informed")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    @NotNull(message = "The endDate must be informed")
    private Date endDate;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Institution institution;

    public void update(AcademicTerm updatedAcademicTerm) {
        this.code = updatedAcademicTerm.getCode();
        this.year = updatedAcademicTerm.getYear();
        this.startDate = updatedAcademicTerm.getStartDate();
        this.endDate = updatedAcademicTerm.getEndDate();
    }

    @Override
    public String toString() {
        if(Objects.isNull(institution)) {
            return "AcademicTerm{" +
                    "idAcademicTerm=" + idAcademicTerm +
                    ", year=" + year +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", institution=" + null +
                    '}';
        }
        return "AcademicTerm{" +
                "idAcademicTerm=" + idAcademicTerm +
                ", year=" + year +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", institution=" + institution.getAcronym() +
                '}';
    }
}
