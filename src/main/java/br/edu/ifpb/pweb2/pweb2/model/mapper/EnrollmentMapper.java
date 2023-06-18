package br.edu.ifpb.pweb2.pweb2.model.mapper;

import br.edu.ifpb.pweb2.pweb2.model.dto.EnrollmentDTO;
import br.edu.ifpb.pweb2.pweb2.model.entity.Enrollment;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    Enrollment toEntity(EnrollmentDTO enrollmentDTO);

    EnrollmentDTO toDTO(Enrollment enrollment);

    List<EnrollmentDTO> toDTOList(List<Enrollment> enrollmentList);
}
