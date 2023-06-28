package br.edu.ifpb.pweb2.pweb2.repository;

import br.edu.ifpb.pweb2.pweb2.model.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.model.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicTermRepository extends JpaRepository<AcademicTerm, Integer> {

    List<AcademicTerm> findAllByInstitution(Institution institution);

    Page<AcademicTerm> getAllByInstitution(Institution institution, Pageable pageable);

    boolean existsByYearAndSemesterAndInstitution(Integer year, Integer semester, Institution institution);

    boolean existsByYearAndSemesterAndInstitutionAndIdAcademicTermNot(Integer year, Integer semester,
                                                                      Institution institution, Integer idAcademicTerm);

}
