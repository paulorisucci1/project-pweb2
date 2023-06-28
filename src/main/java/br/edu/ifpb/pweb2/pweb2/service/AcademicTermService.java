package br.edu.ifpb.pweb2.pweb2.service;

import br.edu.ifpb.pweb2.pweb2.exceptions.EntityAlreadyExistException;
import br.edu.ifpb.pweb2.pweb2.exceptions.EntityNotFoundException;
import br.edu.ifpb.pweb2.pweb2.model.AcademicTerm;
import br.edu.ifpb.pweb2.pweb2.repository.AcademicTermRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AcademicTermService {

    private AcademicTermRepository academicTermRepository;

    private InstitutionService institutionService;

    @Transactional
    public AcademicTerm createAcademicTermForInstitution(AcademicTerm academicTerm, Integer idInstitution) {
        final var createdAcademicTerm = saveAcademicTerm(academicTerm);
        institutionService.addAcademicTermOntoInstitution(createdAcademicTerm, idInstitution);
        verifyIfAcademicTermExist(academicTerm);
        return createdAcademicTerm;
    }

    public AcademicTerm update(AcademicTerm updatedAcademicTerm) {
        verifyIfAcademicTermExist(updatedAcademicTerm);

        final var foundAcademicTerm = searchById(updatedAcademicTerm.getIdAcademicTerm());

        foundAcademicTerm.update(updatedAcademicTerm);

        return saveAcademicTerm(foundAcademicTerm);
    }

    public List<AcademicTerm> listAcademicTermsOfInstitution(Integer idInstitution) {
        final var institution = institutionService.searchById(idInstitution);
        return academicTermRepository.findAllByInstitution(institution);
    }

    public AcademicTerm searchById(Integer id) {
        return academicTermRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Academic term not found"));
    }

    @Transactional
    public void delete(Integer id) {
        final var removedAcademicTerm = searchById(id);
        institutionService.removeAcademicTermFromInstitution(removedAcademicTerm);
        academicTermRepository.delete(removedAcademicTerm);
    }

    private void verifyIfAcademicTermExist(AcademicTerm academicTerm) {
        if(academicTermRepository.existsByYearAndSemesterAndInstitutionAndIdAcademicTermNot(academicTerm.getYear(),
                academicTerm.getSemester(), academicTerm.getInstitution(), academicTerm.getIdAcademicTerm())) {
            throw new EntityAlreadyExistException("The academic term year and semester already exist.");
        }
    }

    private AcademicTerm saveAcademicTerm(AcademicTerm academicTerm) {
        return academicTermRepository.save(academicTerm);
    }

}
