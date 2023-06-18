package br.edu.ifpb.pweb2.pweb2.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@Data
@EqualsAndHashCode(exclude = "institutionDTO")
public class AcademicTermDTO {

    private Integer idAcademicTerm;

    @NotNull(message = "The year must be informed")
    private Integer year;

    @NotNull(message = "The semester must be informed")
    @Digits(integer = 1, fraction = 0, message = "The semester must be an integer")
    private Integer semester;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "The startDate must be informed")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "The endDate must be informed")
    private Date endDate;

    private InstitutionDTO institution;

    @JsonIgnore
    public String getCode() {
        return year+"."+semester;
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
