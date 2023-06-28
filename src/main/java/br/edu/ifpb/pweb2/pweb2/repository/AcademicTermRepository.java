package br.edu.ifpb.pweb2.pweb2.repository;

import br.edu.ifpb.pweb2.pweb2.model.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicTermRepository extends JpaRepository<AcademicTerm, Integer> {

    List<AcademicTerm> findAllByInstitution(Institution institution);

    boolean existsByYearAndSemesterAndInstitution(Integer year, Integer semester, Institution institution);

    boolean existsByYearAndSemesterAndInstitutionAndIdAcademicTermNot(Integer year, Integer semester,
                                                                      Institution institution, Integer idAcademicTerm);

}
