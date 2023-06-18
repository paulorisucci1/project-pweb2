package br.edu.ifpb.pweb2.pweb2.model.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class InstitutionDTO {

    private Integer idInstitution;

    @Size(min = 3, max = 10, message = "The institution acronym must have between 3 an 10 characters.")
    @NotBlank(message = "The institution acronym must be informed.")
    private String acronym;

    @Size(min = 11, max=20 , message = "The institution phone must have between 11 an 20 characters.")
    @NotBlank(message = "The institution phone must be informed.")
    @Digits(integer = 20, fraction = 0, message = "The phone must be a number.")
    private String phone;

    private List<AcademicTermDTO> academicTermList;

    private AcademicTermDTO currentAcademicTerm;

    @Override
    public String toString() {
        return "Institution{" +
                "idInstitution=" + idInstitution +
                ", acronym='" + acronym + '\'' +
                ", phone='" + phone + '\'' +
                ", academicTermList=" + academicTermList +
                ", currentAcademicTerm=" + currentAcademicTerm +
                '}';
    }
}
