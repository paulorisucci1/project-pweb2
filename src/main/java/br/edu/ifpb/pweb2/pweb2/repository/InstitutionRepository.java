package br.edu.ifpb.pweb2.pweb2.repository;

import br.edu.ifpb.pweb2.pweb2.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

    boolean existsByAcronymAndIdInstitutionNot(String acronym, Integer idInstitution);

    boolean existsByAcronym(String acronym);

}
