package br.edu.ifpb.pweb2.pweb2.model.mapper;

import br.edu.ifpb.pweb2.pweb2.model.dto.InstitutionDTO;
import br.edu.ifpb.pweb2.pweb2.model.entity.Institution;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InstitutionMapper {

    Institution toEntity(InstitutionDTO institutionDTO);

    InstitutionDTO toDTO(Institution institution);

    List<InstitutionDTO> toDTOList(List<Institution> institutionList);
}
