package br.edu.ifpb.pweb2.pweb2.model.mapper;

import br.edu.ifpb.pweb2.pweb2.model.dto.AcademicTermDTO;
import br.edu.ifpb.pweb2.pweb2.model.entity.AcademicTerm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AcademicTermMapper {
    AcademicTerm toEntity(AcademicTermDTO academicTermDTO);

    @Mapping(source = "institution", target = "")
    AcademicTermDTO toDTO(AcademicTerm academicTerm);

    java.util.List<AcademicTermDTO> toDTOList(List<AcademicTerm> academicTermList);
}
