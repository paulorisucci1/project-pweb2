package br.edu.ifpb.pweb2.pweb2.repository;

import br.edu.ifpb.pweb2.pweb2.model.entity.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.model.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicTermRepository extends JpaRepository<AcademicTerm, Integer> {

    List<AcademicTerm> findAllByInstitution(Institution institution);

}
