package br.edu.ifpb.pweb2.pweb2.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(exclude = "currentEnrollmentDTO")
public class StudentDTO {

    private Integer idStudent;

    @NotNull(message = "The name must not be null")
    private String name;

    @NotNull(message = "The registration must not be null.")
    private String registration;

    private InstitutionDTO institutionDTO;

    private List<EnrollmentDTO> enrollmentsDTO;

    private EnrollmentDTO currentEnrollmentDTO;
}
